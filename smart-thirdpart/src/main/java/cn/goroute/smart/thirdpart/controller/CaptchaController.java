package cn.goroute.smart.thirdpart.controller;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.thirdpart.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "三方接口")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/register/captcha")
    @ApiOperation(value = "获取注册验证码", notes = "获取注册验证码", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emailAddress", value = "邮箱地址", required = true, dataType = "String", paramType = "query")
    })
    public Response generateRegistrationVerificationCode(@RequestParam(value = "emailAddress") String emailAddress){

        return captchaService.generateRegistrationVerificationCode(emailAddress);

    }


}
