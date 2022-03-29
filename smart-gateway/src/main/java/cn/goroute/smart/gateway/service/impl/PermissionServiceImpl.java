package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.common.dao.PermissionDao;
import cn.goroute.smart.common.entity.pojo.Permission;
import cn.goroute.smart.gateway.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {


}