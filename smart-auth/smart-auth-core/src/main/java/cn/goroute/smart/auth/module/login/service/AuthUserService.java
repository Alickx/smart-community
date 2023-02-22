package cn.goroute.smart.auth.module.login.service;

import cn.goroute.smart.auth.domain.entity.AuthUserEntity;
import cn.goroute.smart.auth.domain.form.UserLoginForm;
import cn.goroute.smart.auth.domain.form.UserRegisterForm;
import cn.goroute.smart.auth.domain.vo.CustomUserDetailsVO;
import cn.goroute.smart.common.modules.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author caiguopeng
* @description 针对表【auth_user(用户授权表)】的数据库操作Service
* @createDate 2022-09-17 19:21:38
*/
public interface AuthUserService extends IService<AuthUserEntity> {

    /**
     * 用户登录
     * @param userLoginForm 用户登录信息
     * @return 用户信息
     */
    R<CustomUserDetailsVO> login(UserLoginForm userLoginForm);

    /**
     * 用户注册
     * @param userRegisterForm 用户注册信息
     * @return 用户信息
     */
    R<Boolean> register(UserRegisterForm userRegisterForm);

    /**
     * 登出
     * @return 登出结果
     */
    R<Boolean> logout();

    R<Boolean> checkName(String username);

    R<Boolean> checkEmail(String email);

    R<Boolean> activate(String token);
}
