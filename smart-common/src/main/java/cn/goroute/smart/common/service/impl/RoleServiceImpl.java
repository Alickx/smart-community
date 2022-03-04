package cn.goroute.smart.common.service.impl;

import cn.goroute.smart.common.dao.RoleDao;
import cn.goroute.smart.common.entity.RoleEntity;
import cn.goroute.smart.common.service.RoleService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
        );

        return new PageUtils(page);
    }

}