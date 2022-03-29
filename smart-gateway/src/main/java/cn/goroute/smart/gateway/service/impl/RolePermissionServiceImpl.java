package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.common.dao.RolePermissionDao;
import cn.goroute.smart.common.entity.pojo.RolePermission;
import cn.goroute.smart.gateway.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {


}