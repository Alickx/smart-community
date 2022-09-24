package cn.goroute.smart.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.auth.domain.AuthUser;
import cn.goroute.smart.auth.entity.CustomUserDetails;
import cn.goroute.smart.auth.entity.vo.UserLoginVo;
import cn.goroute.smart.auth.entity.vo.UserRegisterVo;
import cn.goroute.smart.auth.mapper.AuthUserMapper;
import cn.goroute.smart.auth.service.AuthUserService;
import cn.goroute.smart.auth.service.FeignUserProfileService;
import cn.goroute.smart.auth.service.PermissionService;
import cn.goroute.smart.auth.service.RoleService;
import cn.goroute.smart.auth.strategy.register.RegisterEnum;
import cn.goroute.smart.auth.strategy.register.RegisterStrategy;
import cn.goroute.smart.common.constant.ErrorCodeEnum;
import cn.goroute.smart.common.entity.dto.UserProfileDto;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【auth_user(用户授权表)】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUserServiceImpl extends ExtendServiceImpl<AuthUserMapper, AuthUser>
    implements AuthUserService{

    private final AuthUserMapper authUserMapper;

    private final RegisterStrategy registerStrategy;

    private final FeignUserProfileService feignUserProfileService;

    private final RoleService roleService;

    private final PermissionService permissionService;

    /**
     * 用户登录
     *
     * @param userLoginVo 用户登录信息
     * @return 用户信息
     */
    @Override
    public R<CustomUserDetails> login(UserLoginVo userLoginVo) {

        AuthUser authUser = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
            .eq(AuthUser::getIdentifier, userLoginVo.getUsername()));

        if (authUser == null) {
            return R.failed(ErrorCodeEnum.USER_NOT_EXIST);
        }

        if (!BCrypt.checkpw(userLoginVo.getPassword(), authUser.getCertificate())) {
            return R.failed(ErrorCodeEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        Long userId = authUser.getId();

        StpUtil.login(userId);
        String tokenValue = StpUtil.getTokenValue();

        R<UserProfileDto> userProfileResult = feignUserProfileService.getUserProfile(userId);
        if (userProfileResult.getData() == null) {
            log.error("获取用户信息失败,用户信息:{}",userLoginVo);
            return R.failed(ErrorCodeEnum.SYSTEM_ERROR);
        }

        R<List<String>> permissionResult = permissionService.getPermission(userId);
        if (permissionResult.getData() == null) {
            log.error("获取用户权限失败,用户信息:{}",userLoginVo);
            return R.failed(ErrorCodeEnum.SYSTEM_ERROR);
        }

        R<List<String>> roleResult = roleService.getRole(userId);
        if (roleResult.getData() == null) {
            log.error("获取用户角色失败,用户信息:{}",userLoginVo);
            return R.failed(ErrorCodeEnum.SYSTEM_ERROR);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails
                .setAccessToken(tokenValue)
                .setUserProfile(userProfileResult.getData())
                .setPermissions(permissionResult.getData())
                .setRoles(roleResult.getData());

        return R.ok(customUserDetails);

    }

    /**
     * 用户注册
     *
     * @param userRegisterVo 用户注册信息
     * @return 用户信息
     */
    @Override
    public R<Boolean> register(UserRegisterVo userRegisterVo) {

        AuthUser authUser = authUserMapper.selectOne(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getIdentifier, userRegisterVo.getUsername()));

        if (authUser != null) {
            return R.failed(ErrorCodeEnum.USER_ALREADY_EXIST);
        }

        RegisterEnum registerEnum = RegisterEnum.getRegisterEnum(userRegisterVo.getIdentityType());
        if (registerEnum == null) {
            return R.failed(ErrorCodeEnum.PARAM_ERROR);
        }
        registerStrategy
                .setRegisterExecute(registerEnum);
        registerStrategy.register(userRegisterVo);

        return R.ok();

    }

    /**
     * 登出
     *
     * @return 登出结果
     */
    @Override
    public R<Boolean> logout() {
        StpUtil.logout();
        return R.ok();
    }
}




