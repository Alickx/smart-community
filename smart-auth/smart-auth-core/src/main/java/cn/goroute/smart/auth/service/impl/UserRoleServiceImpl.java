package cn.goroute.smart.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.auth.domain.UserRole;
import cn.goroute.smart.auth.service.UserRoleService;
import cn.goroute.smart.auth.mapper.UserRoleMapper;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
public class UserRoleServiceImpl extends ExtendServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




