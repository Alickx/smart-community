package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.gateway.entity.pojo.Role;
import cn.goroute.smart.gateway.service.RoleService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;


@Service("roleService")
public class RoleServiceImpl extends ExtendServiceImpl<RoleMapper, Role> implements RoleService {


}