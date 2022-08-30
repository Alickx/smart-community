package cn.goroute.smart.thirdpart.controller;

import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.thirdpart.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/21:47
 * @Description: 验证码控制器
 */
@RestController
@RequestMapping("smart/thirdpart")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/register/captcha")
    public Result generateRegistrationVerificationCode(@RequestParam(value = "emailAddress") String emailAddress){

        return captchaService.generateRegistrationVerificationCode(emailAddress);

    }


}
