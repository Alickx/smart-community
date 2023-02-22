package cn.goroute.smart.auth.module.login.service.impl;

import cn.goroute.smart.auth.domain.entity.UserRoleEntity;
import cn.goroute.smart.auth.module.login.mapper.UserRoleMapper;
import cn.goroute.smart.auth.module.login.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity>
    implements UserRoleService{

}




