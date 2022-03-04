package cn.goroute.smart.common.service.impl;

import cn.goroute.smart.common.dao.RolePermissionDao;
import cn.goroute.smart.common.entity.RolePermissionEntity;
import cn.goroute.smart.common.service.RolePermissionService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermissionEntity> implements RolePermissionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolePermissionEntity> page = this.page(
                new Query<RolePermissionEntity>().getPage(params),
                new QueryWrapper<RolePermissionEntity>()
        );

        return new PageUtils(page);
    }

}