package cn.goroute.smart.gateway.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.gateway.mapper.PermissionMapper;
import cn.goroute.smart.gateway.mapper.RoleMapper;
import cn.goroute.smart.gateway.mapper.RolePermissionMapper;
import cn.goroute.smart.gateway.mapper.UserRoleMapper;
import cn.goroute.smart.gateway.entity.pojo.Permission;
import cn.goroute.smart.gateway.entity.pojo.RolePermission;
import cn.goroute.smart.gateway.entity.pojo.UserRole;
import cn.goroute.smart.common.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = new ArrayList<>();

        List<UserRole> userRoleList = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserUid, loginId));
        if (userRoleList == null) {
            return permissionList;
        }
        for (UserRole userRole : userRoleList) {
            Long roleUid = userRole.getRoleUid();
            List<RolePermission> rolePermissionList = rolePermissionMapper
                    .selectList(new LambdaQueryWrapper<RolePermission>()
                            .eq(RolePermission::getRoleUid, roleUid));
            if (rolePermissionList == null) {
                return permissionList;
            }
            for (RolePermission rolePermission : rolePermissionList) {
                Permission permission = permissionMapper.selectById(rolePermission.getPermissionUid());
                if (permission != null) {
                    permissionList.add(permission.getPermissionName());
                }
            }
        }

        redisUtil.set(RedisKeyConstant.PERMISSION_LIST_KEY + loginId, permissionList);

        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {


        List<String> roleNameByMemberUid = roleMapper.getRoleNameByMemberUid((Long) loginId);

        redisUtil.set(RedisKeyConstant.ROLE_LIST_KEY + loginId, roleNameByMemberUid);

        return roleNameByMemberUid;
    }
}
