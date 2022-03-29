package cn.goroute.smart.gateway.controller;

import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.entity.pojo.Member;
import cn.goroute.smart.common.entity.vo.MemberRegisterVO;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.*;
import cn.goroute.smart.gateway.feign.MemberFeignService;
import cn.goroute.smart.gateway.util.MailSender;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@Slf4j
@RequestMapping("/api/v1/register")
public class RegisterApi {

    private static final int BAN_TIME = 10 * 60;

    //统计20分钟内最多发送邮件
    private static final int MAX_SEND_COUNT = 10;

    //统计注册总次数为20分钟
    private static final int REG_SEND_EXPIRE_TIME = 60 * 20;

    //验证码过期时间
    private static final int REG_CAPTCHA_EXPIRE_TIME = 60 * 10;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    MailSender mailSender;

    @Autowired
    MemberDao memberDao;


    @PostMapping
    public Result register(@RequestBody MemberRegisterVO memberVo) {

        String memberRegEmail = memberVo.getUsername();

        String regBanKey = RedisKeyConstant.REG_SEND_BAN_KEY + memberRegEmail;
        //如果禁止注册则没必要去查询了
        if (redisUtil.hasKey(regBanKey)) {
            return Result.error("错误次数过多，已被禁止注册10分钟");
        }

        //校验是否存在该用户
        Member memberIsExist = memberDao.selectOne(new QueryWrapper<Member>().eq("email", memberRegEmail));

        if (memberIsExist != null) {
            return Result.error("该邮箱已经注册，请换一个吧");
        }

        String regKey = RedisKeyConstant.REG_CAPTCHA_KEY + memberRegEmail;

        //校验验证码是否过期或存在
        if (!redisUtil.hasKey(regKey)) {
            return Result.error(RespCode.CAPTCHA_ERROR, "验证码已过期或不存在");
        }

        int errorCount = (int) redisUtil.hget(regKey, "errorCount");

        if (errorCount >= 5) {
            //当错误次数超过5次后，对该邮箱禁止注册10分钟
            String banKey = RedisKeyConstant.REG_SEND_BAN_KEY + memberRegEmail;

            redisUtil.set(banKey, "", BAN_TIME);
            redisUtil.del(regKey);

            return Result.error(RespCode.CAPTCHA_ERROR, "错误次数过多，已被禁止注册10分钟");
        }
        String memberRegCaptcha = (String) redisUtil.hget(regKey, "captcha");

        //错误则直接返回,且错误次数增加
        if (!Objects.equals(memberRegCaptcha, memberVo.getCaptcha().toLowerCase())) {
            log.info("memberRegCaptcha={},memberRegVo={}", memberRegCaptcha, memberVo.getCaptcha().toLowerCase());
            if (redisUtil.hHasKey(regKey, "captcha")) {
                redisUtil.hincr(regKey, "errorCount", 1);
            }

            return Result.error(RespCode.CAPTCHA_ERROR, "验证码错误");
        }

        //正确则执行注册流程
        Member member = new Member();
        String memberRegVoPassWord = memberVo.getPassWord();
        String bcryptPassWord = DigestUtil.bcrypt(memberRegVoPassWord);

        //存储用户数据
        member.setPassWord(bcryptPassWord);
        member.setEmail(memberRegEmail);
        int registerResult = memberDao.insert(member);
        if (registerResult != 1) {
            log.error("用户{}注册失败，注册验证码为{}", memberRegEmail, memberRegCaptcha);
        }
        String regCountKey = RedisKeyConstant.REG_SEND_COUNT_KEY + memberRegEmail;

        redisUtil.del(regKey, regCountKey);
        return Result.ok("用户注册成功");

    }

    @GetMapping("/captcha")
    public Result sendCaptcha(@RequestParam String emailAddress) {

        String regCaptchaKey = RedisKeyConstant.REG_CAPTCHA_KEY + emailAddress;

        String regBanKey = RedisKeyConstant.REG_SEND_BAN_KEY + emailAddress;

        String regCountKey = RedisKeyConstant.REG_SEND_COUNT_KEY + emailAddress;

        String regSleepKey = RedisKeyConstant.REG_SEND_SLEEP_KEY + emailAddress;

        //检查是否在被Ban列表中
        if (redisUtil.hasKey(regBanKey)) {
            return Result.error("错误次数过多，禁止注册10分钟");
        }

        //检查邮箱发送冷却是否已过期
        if (redisUtil.hasKey(regSleepKey)) {
            return Result.error("请等一会再发送验证码");
        }

        //检查邮箱是否超过一天的发送限度
        if (redisUtil.hasKey(regCountKey)) {
            int regCount = (int) redisUtil.get(regCountKey);
            if (regCount >= MAX_SEND_COUNT) {
                return Result.error("该邮箱发送太多邮件，请明天再来");
            }
        }

        //调用前查询是否已经存在该邮箱
        Result result = memberFeignService.infoMemberEmail(emailAddress);

        Boolean emailIsExist = (Boolean) result.get("data");

        if (emailIsExist) {
            return Result.error("该邮箱已经注册，请换一个吧");
        }

        //使用redis添加验证码
        String randomCaptcha = RandomUtil.randomString(6);

        //如果重新发送验证码则会覆盖原先的验证码
        redisUtil.hset(regCaptchaKey, "captcha", randomCaptcha);
        //如果存在则覆盖掉原来的错误次数，如果不存在则创建
        redisUtil.hset(regCaptchaKey, "errorCount", 0);

        //设置验证码的过期时间
        redisUtil.expire(regCaptchaKey,REG_CAPTCHA_EXPIRE_TIME);

        //设置发送验证码的冷却时间 60秒
        redisUtil.set(regSleepKey, "", 60);

        log.info("key={}",regCaptchaKey);

        //发送邮件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String subject = "智慧社区用户注册";
        String emailTemplate = "registerTemplate";
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", emailAddress);
        dataMap.put("code", randomCaptcha.toUpperCase());
        dataMap.put("createTime", sdf.format(new Date()));
        try {
            mailSender.sendCaptcha(emailAddress, subject, emailTemplate, dataMap);
        } catch (Exception e) {
            throw new ServiceException(ExceptionUtil.getMessage(e));
        }

        //该邮箱发送次数+1
        if (redisUtil.hasKey(regCountKey)) {
            redisUtil.incr(regCountKey,1);
        } else {
            //如果该邮箱没有发送过，则新增key且过期时间为20分钟
            redisUtil.set(regCountKey, REG_SEND_EXPIRE_TIME, 1);
        }


        return Result.ok();
    }

}
