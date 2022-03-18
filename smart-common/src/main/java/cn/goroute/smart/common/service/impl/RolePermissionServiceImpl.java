package cn.goroute.smart.common.service.impl;

import cn.goroute.smart.common.dao.RolePermissionDao;
import cn.goroute.smart.common.entity.pojo.RolePermissionEntity;
import cn.goroute.smart.common.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermissionEntity> implements RolePermissionService {


}