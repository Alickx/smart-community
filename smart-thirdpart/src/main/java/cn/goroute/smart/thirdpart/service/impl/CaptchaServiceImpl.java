package cn.goroute.smart.thirdpart.service.impl;

import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.feign.MemberFeignService;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.redis.util.RedisUtil;
import cn.goroute.smart.thirdpart.service.CaptchaService;
import cn.goroute.smart.thirdpart.util.SendMailUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/21:58
 * @Description: 验证码逻辑类
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SendMailUtil sendMailUtil;

    @Autowired
    MemberFeignService memberFeignService;


    /**
     * 统计20分钟内最多发送邮件的次数
     */
    private static final int MAX_SEND_COUNT = 10;

    /**
     * 根据第一次发送邮件的时间开始计算
     */
    private static final int REG_SEND_EXPIRE_TIME = 60 * 20;

    /**
     * 验证码过期时间
     */
    private static final int REG_CAPTCHA_EXPIRE_TIME = 60 * 5;

    /**
     * 验证码间隔时间
     */
    private static final int REG_CAPTCHA_INTERVAL_TIME = 60;

    /**
     * 生成发送验证码
     *
     * @param emailAddress 邮箱地址
     * @return 发送结果
     */
    @Override
    public Result generateRegistrationVerificationCode(String emailAddress) {

        /**
         * 注册验证码的键值（jhr）对应
         */
        String regCaptchaKey = RedisKeyConstant.REG_CAPTCHA_KEY + emailAddress;

        /**
         * 禁止注册的键值（恶意注册）
         */
        String regBanKey = RedisKeyConstant.REG_SEND_BAN_KEY + emailAddress;

        /**
         * 统计注册的键值
         */
        String regCountKey = RedisKeyConstant.REG_SEND_COUNT_KEY + emailAddress;

        /**
         * 注册间隔的键值（60秒）
         */
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
       //调用用户微服务模块查看邮箱是否被注册
        Result result = memberFeignService.queryUserEmail(emailAddress);
        if ("500".equals(result.get("code"))) {
            return Result.error("该邮箱已被注册");
        }

        //使用redis添加验证码,随机六位英文字符
        String randomCaptcha = RandomUtil.randomString(6);

        //如果重新发送验证码则会覆盖原先的验证码
        redisUtil.hset(regCaptchaKey, "captcha", randomCaptcha);
        //如果存在则覆盖掉原来的错误次数，如果不存在则创建
        redisUtil.hset(regCaptchaKey, "errorCount", 0);

        //设置验证码的过期时间
        redisUtil.expire(regCaptchaKey, REG_CAPTCHA_EXPIRE_TIME);

        //设置发送验证码的冷却时间 60秒
        redisUtil.set(regSleepKey, "", REG_CAPTCHA_INTERVAL_TIME);

        log.info("key={}", regCaptchaKey);

        //发送邮件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String subject = "智慧社区用户注册";
        String emailTemplate = "registerTemplate";
        Map<String, Object> dataMap = new HashMap<>(5);
        dataMap.put("email", emailAddress);
        dataMap.put("code", randomCaptcha.toUpperCase());
        dataMap.put("createTime", sdf.format(new Date()));
        try {
            sendMailUtil.sendTemplateMail(emailAddress, subject, emailTemplate, dataMap);
        } catch (Exception e) {
            log.error("发送邮件失败，邮箱地址为：{}", emailAddress);
            throw new ServiceException(e.getMessage(), e);
        }

        //该邮箱发送次数+1
        if (redisUtil.hasKey(regCountKey)) {
            redisUtil.incr(regCountKey, 1);
        } else {
            //如果该邮箱没有发送过，则新增key且过期时间为20分钟
            redisUtil.set(regCountKey, REG_SEND_EXPIRE_TIME, 1);
        }

        return Result.ok();
    }
}
