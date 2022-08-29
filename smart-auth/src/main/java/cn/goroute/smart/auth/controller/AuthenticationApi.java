package cn.goroute.smart.auth.controller;

import cn.goroute.smart.auth.entity.vo.MemberLoginVo;
import cn.goroute.smart.auth.entity.vo.MemberRegisterVo;
import cn.goroute.smart.auth.service.AuthenticationService;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/20:55
 * @Description: 用户登录，注册，注销
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationApi {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public Result login(@RequestBody MemberLoginVo memberLoginVO, HttpServletRequest request) {
        return authenticationService.login(memberLoginVO, request);
    }

    @PostMapping("/register")
    public Result register(@RequestBody MemberRegisterVo memberRegisterVO) {
        return authenticationService.register(memberRegisterVO);
    }

}
