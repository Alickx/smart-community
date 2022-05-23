package cn.goroute.smart.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.auth.service.AuthenticationService;
import cn.goroute.smart.common.entity.vo.MemberLoginVO;
import cn.goroute.smart.common.entity.vo.MemberRegisterVO;
import cn.goroute.smart.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public Result login(@RequestBody MemberLoginVO memberLoginVO, HttpServletRequest request) {
        return authenticationService.login(memberLoginVO, request);
    }

    @PostMapping("/logout")
    public Result logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
            return Result.ok();
        }
        return Result.error("用户未登录");
    }

    @PostMapping("/register")
    public Result register(@RequestBody MemberRegisterVO memberRegisterVO) {
        return authenticationService.register(memberRegisterVO);
    }


}
