package cn.goroute.smart.auth.service.impl;

import cn.goroute.smart.auth.domain.Role;
import cn.goroute.smart.auth.mapper.RoleMapper;
import cn.goroute.smart.auth.mapper.UserRoleMapper;
import cn.goroute.smart.auth.service.RoleService;
import cn.hutool.core.collection.CollUtil;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.redis.core.annotation.Cached;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author caiguopeng
* @description 针对表【role】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ExtendServiceImpl<RoleMapper, Role>
    implements RoleService{

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    /**
     * 获取角色
     * @return 角色列表
     */
    @Override
    @Cached(key = "role:",keyJoint = "#userId", ttl = 120)
    public R<List<String>> getRole(Long userId) {

        List<Long> roleIds = userRoleMapper.selectByUserId(userId);

        if (CollUtil.isEmpty(roleIds)) {
            return R.ok(Collections.emptyList());
        }

        List<Role> roles = roleMapper.selectBatchIds(roleIds);
        List<String> roleList =
                roles.stream().map(Role::getRoleName).collect(Collectors.toList());

        if (CollUtil.isEmpty(roleList)) {
            roles = Collections.emptyList();
        }

        return R.ok(roleList);

    }
}




