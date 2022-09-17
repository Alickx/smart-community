package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.gateway.mapper.UserRoleMapper;
import cn.goroute.smart.gateway.entity.pojo.UserRole;
import cn.goroute.smart.gateway.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;


@Service("userRoleService")
public class UserRoleServiceImpl extends ExtendServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {


}