package cn.goroute.smart.auth.module.login.service.impl;

import cn.goroute.smart.auth.domain.entity.PermissionEntity;
import cn.goroute.smart.auth.module.login.mapper.PermissionMapper;
import cn.goroute.smart.auth.module.login.mapper.RolePermissionMapper;
import cn.goroute.smart.auth.module.login.mapper.UserRoleMapper;
import cn.goroute.smart.auth.module.login.service.PermissionService;
import cn.goroute.smart.common.modules.result.R;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author caiguopeng
* @description 针对表【permission】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity>
    implements PermissionService{

    private final RolePermissionMapper rolePermissionMapper;

    private final UserRoleMapper userRoleMapper;

    /**
     * 获取权限
     * @return 权限列表
     */
    @Override
    public R<List<String>> getPermission(Long userId) {

        List<Long> roleIds = userRoleMapper.selectByUserId(userId);

        if (CollUtil.isEmpty(roleIds)) {
            return R.ok(Collections.emptyList());
        }

        List<String> permissions = rolePermissionMapper.selectByRoleIds(roleIds);

        if (CollUtil.isEmpty(permissions)) {
            permissions = Collections.emptyList();
        }

        return R.ok(permissions);
    }
}




