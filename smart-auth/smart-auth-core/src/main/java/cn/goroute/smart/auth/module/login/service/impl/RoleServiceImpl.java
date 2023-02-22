package cn.goroute.smart.auth.module.login.service.impl;

import cn.goroute.smart.auth.domain.entity.RoleEntity;
import cn.goroute.smart.auth.module.login.mapper.RoleMapper;
import cn.goroute.smart.auth.module.login.mapper.UserRoleMapper;
import cn.goroute.smart.auth.module.login.service.RoleService;
import cn.goroute.smart.common.modules.result.R;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity>
    implements RoleService{

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    /**
     * 获取角色
     * @return 角色列表
     */
    @Override
    public R<List<String>> getRole(Long userId) {

        List<Long> roleIds = userRoleMapper.selectByUserId(userId);

        if (CollUtil.isEmpty(roleIds)) {
            return R.ok(Collections.emptyList());
        }

        List<RoleEntity> roleEntities = roleMapper.selectBatchIds(roleIds);
        List<String> roleList =
                roleEntities.stream().map(RoleEntity::getRoleName).collect(Collectors.toList());

        if (CollUtil.isEmpty(roleList)) {
            roleList = Collections.emptyList();
        }

        return R.ok(roleList);

    }
}




