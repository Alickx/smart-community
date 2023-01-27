package cn.goroute.smart.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.auth.model.CustomUserDetails;
import cn.goroute.smart.auth.model.vo.UserLoginVO;
import cn.goroute.smart.auth.model.vo.UserRegisterVO;
import cn.goroute.smart.auth.service.AuthUserService;
import cn.goroute.smart.auth.service.PermissionService;
import cn.goroute.smart.auth.service.RoleService;
import com.hccake.ballcat.common.model.result.R;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    private final RoleService roleService;

    private final PermissionService permissionService;

    /**
     * 登入
     * @param userLoginVo 用户登录信息
     * @return CustomUserDetails 登录成功的用户信息
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录")
    public R<CustomUserDetails> login(@RequestBody @Valid UserLoginVO userLoginVo) {
        return authUserService.login(userLoginVo);
    }

    /**
     * 注册
     * @param userRegisterVo 注册信息
     * @return R 注册的结果
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册")
    public R<Boolean> register(@RequestBody @Valid UserRegisterVO userRegisterVo) {
        return authUserService.register(userRegisterVo);
    }

    /**
     * 登出
     * @return R 登出的结果
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出")
    public R<Boolean> logout() {
        return authUserService.logout();
    }

    /**
     * 获取当前用户的权限
     * @return R<List<String>> 权限列表
     */
    @GetMapping("/permission")
    @Operation(summary = "获取当前用户的权限", description = "获取当前用户的权限")
    public R<List<String>> permission() {
        long userId = StpUtil.getLoginIdAsLong();
        return permissionService.getPermission(userId);
    }

    /**
     * 获取当前用户的角色
     * @return R<List<String>> 角色列表
     */
    @GetMapping("/role")
    @Operation(summary = "获取当前用户的角色", description = "获取当前用户的角色")
    public R<List<String>> role() {
        long userId = StpUtil.getLoginIdAsLong();
        return roleService.getRole(userId);
    }




}
