package cn.goroute.smart.thirdpart.controller;

import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.thirdpart.feign.MemberFeignService;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.goroute.smart.common.utils.Constant.*;

@RestController
@Slf4j
@RequestMapping("thirdpart/captcha")
public class CaptchaController {

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    RedisUtil redisUtil;

    //统计20分钟内最多发送邮件
    private static final int MAX_SEND_COUNT = 10;

    //统计注册总次数为20分钟
    private static final int REG_SEND_EXPIRE_TIME = 60 * 20;

    private static final int REG_CAPTCHA_EXPIRE_TIME = 60 * 10;



    @GetMapping("/sendEmailCaptcha")
    public R sendCaptcha(@RequestParam String emailAddress) {

        String regCaptchaKey = REG_CAPTCHA_KEY + emailAddress;

        String regBanKey = REG_SEND_BAN_KEY + emailAddress;

        String regCountKey = REG_SEND_COUNT_KEY + emailAddress;

        String regSleepKey = REG_SEND_SLEEP_KEY + emailAddress;

        //检查是否在被Ban列表中
        if (redisUtil.hasKey(regBanKey)) {
            return R.error("错误次数过多，禁止注册10分钟");
        }

        //检查邮箱发送冷却是否已过期
        if (redisUtil.hasKey(regSleepKey)) {
            return R.error("请等一会再发送验证码");
        }

        //检查邮箱是否超过一天的发送限度
        if (redisUtil.hasKey(regCountKey)) {
            int regCount = (int) redisUtil.get(regCountKey);
            if (regCount >= MAX_SEND_COUNT) {
                return R.error("该邮箱发送太多邮件，请明天再来");
            }
        }

        //调用前查询是否已经存在该邮箱
        R result = memberFeignService.infoMemberEmail(emailAddress);

        Boolean emailIsExist = (Boolean) result.get("data");

        if (emailIsExist) {
            return R.error("该邮箱已经注册，请换一个吧");
        }

        //使用redis添加验证码
        String randomCaptcha = RandomUtil.randomString(6);

        //TODO 调用第三方邮件接口

        //如果重新发送验证码则会覆盖原先的验证码
        redisUtil.hset(regCaptchaKey, "captcha", randomCaptcha);
        //如果存在则不作任何操作，如果不存在则创建
        redisUtil.hset(regCaptchaKey, "errorCount", 0);


        redisUtil.expire(regCaptchaKey,REG_CAPTCHA_EXPIRE_TIME);

        //设置发送验证码的冷却时间 60秒 TODO 改进使用优先队列来解决频率
        redisUtil.set(regSleepKey, "", 60);

        log.info("key={}",regCaptchaKey);

        //该邮箱发送次数+1
        if (redisUtil.hasKey(regCountKey)) {
            redisUtil.incr(regCountKey,1);
        } else {
            //如果该邮箱没有发送过，则新增key且过期时间为20分钟
            redisUtil.set(regCountKey, REG_SEND_EXPIRE_TIME, 1);
        }


        return R.ok();
    }
}