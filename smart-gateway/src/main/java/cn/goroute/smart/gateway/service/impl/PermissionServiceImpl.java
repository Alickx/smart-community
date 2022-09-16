package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.gateway.entity.pojo.Permission;
import cn.goroute.smart.gateway.mapper.PermissionMapper;
import cn.goroute.smart.gateway.service.PermissionService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;


@Service("permissionService")
public class PermissionServiceImpl extends ExtendServiceImpl<PermissionMapper, Permission> implements PermissionService {


}