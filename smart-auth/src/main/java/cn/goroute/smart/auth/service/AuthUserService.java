package cn.goroute.smart.auth.service;

import cn.goroute.smart.auth.domain.AuthUser;
import cn.goroute.smart.auth.entity.CustomUserDetails;
import cn.goroute.smart.auth.entity.vo.UserLoginVO;
import cn.goroute.smart.auth.entity.vo.UserRegisterVO;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

/**
* @author caiguopeng
* @description 针对表【auth_user(用户授权表)】的数据库操作Service
* @createDate 2022-09-17 19:21:38
*/
public interface AuthUserService extends ExtendService<AuthUser> {

    /**
     * 用户登录
     * @param userLoginVo 用户登录信息
     * @return 用户信息
     */
    R<CustomUserDetails> login(UserLoginVO userLoginVo);

    /**
     * 用户注册
     * @param userRegisterVo 用户注册信息
     * @return 用户信息
     */
    R<Boolean> register(UserRegisterVO userRegisterVo);

    /**
     * 登出
     * @return 登出结果
     */
    R<Boolean> logout();
}
