package cn.goroute.smart.auth.controller;

import cn.goroute.smart.auth.entity.vo.MemberLoginVo;
import cn.goroute.smart.auth.entity.vo.MemberRegisterVo;
import cn.goroute.smart.auth.service.AuthenticationService;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.common.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/20:55
 * @Description: 用户登录，注册
 */
@RestController
@RequestMapping("smart/auth")
@Api(tags = "用户登录，注册")
public class SmartAuthController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberLoginVo", value = "用户登录信息", required = true, dataType = "MemberLoginVo", paramType = "body")
    })
    public Response login(@RequestBody MemberLoginVo memberLoginVO, HttpServletRequest request) {
        return authenticationService.login(memberLoginVO, request);
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberRegisterVo", value = "用户注册信息", required = true, dataType = "MemberRegisterVo", paramType = "body")
    })
    public Response register(@RequestBody MemberRegisterVo memberRegisterVO) {
        return authenticationService.register(memberRegisterVO);
    }

}
