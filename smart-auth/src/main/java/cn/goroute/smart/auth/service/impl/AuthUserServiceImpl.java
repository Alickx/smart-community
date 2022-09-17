package cn.goroute.smart.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.auth.domain.AuthUser;
import cn.goroute.smart.auth.entity.CustomUserDetails;
import cn.goroute.smart.auth.entity.vo.UserLoginVo;
import cn.goroute.smart.auth.entity.vo.UserRegisterVo;
import cn.goroute.smart.auth.mapper.AuthUserMapper;
import cn.goroute.smart.auth.service.AuthUserService;
import cn.goroute.smart.common.constant.ErrorCodeEnum;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【auth_user(用户授权表)】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl extends ExtendServiceImpl<AuthUserMapper, AuthUser>
    implements AuthUserService{

    private final AuthUserMapper authUserMapper;

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

        if (BCrypt.checkpw(userLoginVo.getPassword(), authUser.getCertificate())) {
            return R.failed(ErrorCodeEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        StpUtil.login(authUser.getId());

        // TODO 调用用户信息接口

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails
                .setAccessToken(StpUtil.getTokenValue())
                .setUserProfileDto(null)
                .setPermissions(null)
                .setRoles(null);

        // TODO 事件通知

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
        return null;
    }

    /**
     * 登出
     *
     * @return 登出结果
     */
    @Override
    public R<Boolean> logout() {
        return null;
    }
}




