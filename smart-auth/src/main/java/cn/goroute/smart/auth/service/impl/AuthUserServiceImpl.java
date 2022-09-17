package cn.goroute.smart.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.auth.domain.AuthUser;
import cn.goroute.smart.auth.service.AuthUserService;
import cn.goroute.smart.auth.mapper.AuthUserMapper;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【auth_user(用户授权表)】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
public class AuthUserServiceImpl extends ExtendServiceImpl<AuthUserMapper, AuthUser>
    implements AuthUserService{

}




