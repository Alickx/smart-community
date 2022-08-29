package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.gateway.mapper.RolePermissionMapper;
import cn.goroute.smart.gateway.entity.pojo.RolePermission;
import cn.goroute.smart.gateway.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {


}