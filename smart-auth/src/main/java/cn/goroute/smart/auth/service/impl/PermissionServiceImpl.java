package cn.goroute.smart.auth.service.impl;

import cn.goroute.smart.auth.domain.Permission;
import cn.goroute.smart.auth.mapper.PermissionMapper;
import cn.goroute.smart.auth.mapper.RolePermissionMapper;
import cn.goroute.smart.auth.mapper.UserRoleMapper;
import cn.goroute.smart.auth.service.PermissionService;
import cn.hutool.core.collection.CollUtil;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.redis.core.annotation.CachePut;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
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
public class PermissionServiceImpl extends ExtendServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    private final RolePermissionMapper rolePermissionMapper;

    private final UserRoleMapper userRoleMapper;

    /**
     * 获取权限
     * @return 权限列表
     */
    @Override
    @CachePut(key = "permission",keyJoint = "#userId", ttl = 120)
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




