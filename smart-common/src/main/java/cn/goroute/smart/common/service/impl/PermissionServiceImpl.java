package cn.goroute.smart.common.service.impl;

import cn.goroute.smart.common.dao.PermissionDao;
import cn.goroute.smart.common.entity.pojo.PermissionEntity;
import cn.goroute.smart.common.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, PermissionEntity> implements PermissionService {


}