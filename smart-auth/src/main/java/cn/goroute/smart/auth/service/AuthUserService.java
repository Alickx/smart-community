package cn.goroute.smart.auth.service;

import cn.goroute.smart.auth.domain.AuthUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hccake.extend.mybatis.plus.service.ExtendService;

/**
* @author caiguopeng
* @description 针对表【auth_user(用户授权表)】的数据库操作Service
* @createDate 2022-09-17 19:21:38
*/
public interface AuthUserService extends ExtendService<AuthUser> {

}
