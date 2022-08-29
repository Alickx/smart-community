package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.gateway.mapper.PermissionMapper;
import cn.goroute.smart.gateway.entity.pojo.Permission;
import cn.goroute.smart.gateway.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


}