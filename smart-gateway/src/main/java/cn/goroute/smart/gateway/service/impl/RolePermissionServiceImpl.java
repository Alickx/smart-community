package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.gateway.entity.pojo.RolePermission;
import cn.goroute.smart.gateway.service.RolePermissionService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;


@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ExtendServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {


}