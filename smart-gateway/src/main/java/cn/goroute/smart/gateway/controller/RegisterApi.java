package cn.goroute.smart.gateway.controller;

import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.entity.MemberEntity;
import cn.goroute.smart.common.entity.MemberRegisterVo;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.common.utils.RespCode;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static cn.goroute.smart.common.utils.Constant.*;



@RestController
@Slf4j
@RequestMapping("/register")
public class RegisterApi {

    private static final int BAN_TIME = 10 * 60;

    @Autowired
    MemberDao memberDao;

    @Autowired
    RedisUtil redisUtil;



    @PostMapping
    public R register(@RequestBody MemberRegisterVo memberVo) {

        String memberRegEmail = memberVo.getUsername();

        String regBanKey = REG_SEND_BAN_KEY + memberRegEmail;
        //如果禁止注册则没必要去查询了
        if (redisUtil.hasKey(regBanKey)) {
            return R.error("错误次数过多，已被禁止注册10分钟");
        }

        //校验是否存在该用户
        MemberEntity memberIsExist = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("email", memberRegEmail));

        if (memberIsExist != null) {
            return R.error("该邮箱已经注册，请换一个吧");
        }

        String regKey = REG_CAPTCHA_KEY + memberRegEmail;

        //校验验证码是否过期或存在
        if (!redisUtil.hasKey(regKey)) {
            return R.error(RespCode.CAPTCHA_ERROR,"验证码已过期或不存在");
        }

        int errorCount = (int) redisUtil.hget(regKey, "errorCount");

        if (errorCount >= 5) {
            //当错误次数超过5次后，对该邮箱禁止注册10分钟
            String banKey = REG_SEND_BAN_KEY + memberRegEmail;

            redisUtil.set(banKey,"",BAN_TIME);
            redisUtil.del(regKey);

            return R.error(RespCode.CAPTCHA_ERROR,"错误次数过多，已被禁止注册10分钟");
        }
        String memberRegCaptcha = (String) redisUtil.hget(regKey,"captcha");
        //错误则直接返回,且错误次数增加
        if (!Objects.equals(memberRegCaptcha, memberVo.getCaptcha())) {
            log.info("memberRegCaptcha={},memberRegVo={}",memberRegCaptcha,memberVo.getCaptcha());
            if (redisUtil.hHasKey(regKey,"captcha")) {
                redisUtil.hincr(regKey,"errorCount",1);
            }

            return R.error(RespCode.CAPTCHA_ERROR,"验证码错误");
        }

        //正确则执行注册流程
        MemberEntity member = new MemberEntity();
        String memberRegVoPassWord = memberVo.getPassWord();
        String bcryptPassWord = DigestUtil.bcrypt(memberRegVoPassWord);

        //存储用户数据
        member.setPassWord(bcryptPassWord);
        member.setEmail(memberRegEmail);
        int registerResult = memberDao.insert(member);
        if (registerResult != 1) {
            log.error("用户{}注册失败，注册验证码为{}", memberRegEmail, memberRegCaptcha);
        }
        String regCountKey = REG_SEND_COUNT_KEY + memberRegEmail;

        redisUtil.del(regKey,regCountKey);
        return R.ok("用户注册成功");

    }


}
