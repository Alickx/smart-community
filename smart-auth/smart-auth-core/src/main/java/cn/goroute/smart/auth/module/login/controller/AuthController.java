package cn.goroute.smart.auth.module.login.controller;

import cn.goroute.smart.auth.domain.form.UserLoginForm;
import cn.goroute.smart.auth.domain.form.UserRegisterForm;
import cn.goroute.smart.auth.domain.vo.CustomUserDetailsVO;
import cn.goroute.smart.auth.module.login.service.AuthUserService;
import cn.goroute.smart.common.modules.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/0:14
 * @Description: 登入登出注册控制器
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserService authUserService;
    /**
     * 登入
     * @param userLoginForm 用户登录信息
     * @return CustomUserDetails 登录成功的用户信息
     */
    @PostMapping("/login")
    public R<CustomUserDetailsVO> login(@RequestBody @Valid UserLoginForm userLoginForm) {
        return authUserService.login(userLoginForm);
    }

    /**
     * 注册
     * @param userRegisterForm 注册信息
     * @return R 注册的结果
     */
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody @Valid UserRegisterForm userRegisterForm) {
        return authUserService.register(userRegisterForm);
    }

    /**
     * 登出
     * @return R 登出的结果
     */
    @PostMapping("/logout")
    public R<Boolean> logout() {
        return authUserService.logout();
    }

	/**
	 * 检查用户名是否已经注册
	 * @param username 用户名
	 * @return
	 */
    @GetMapping("/checkName")
    public R<Boolean> checkName(@RequestParam String username) {
        return authUserService.checkName(username);
    }

	/**
	 * 检查邮箱是否已经注册
	 * @param email 邮箱
	 * @return
	 */
    @GetMapping("/checkEmail")
    public R<Boolean> checkEmail(@RequestParam String email) {
        return authUserService.checkEmail(email);
    }

    /**
     * 激活账号
     */
    @PostMapping("/activate")
    public R<Boolean> activate(@RequestParam String token) {
        return authUserService.activate(token);
    }




}
