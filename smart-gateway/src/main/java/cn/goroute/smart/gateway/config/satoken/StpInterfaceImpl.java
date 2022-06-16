package cn.goroute.smart.gateway.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.dao.PermissionDao;
import cn.goroute.smart.common.dao.RoleDao;
import cn.goroute.smart.common.dao.RolePermissionDao;
import cn.goroute.smart.common.dao.UserRoleDao;
import cn.goroute.smart.common.entity.pojo.Permission;
import cn.goroute.smart.common.entity.pojo.RolePermission;
import cn.goroute.smart.common.entity.pojo.UserRole;
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
    UserRoleDao userRoleDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    RolePermissionDao rolePermissionDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = new ArrayList<>();

        List<UserRole> userRoleList = userRoleDao.selectList(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserUid, loginId));
        if (userRoleList == null) {
            return permissionList;
        }
        for (UserRole userRole : userRoleList) {
            Long roleUid = userRole.getRoleUid();
            List<RolePermission> rolePermissionList = rolePermissionDao
                    .selectList(new LambdaQueryWrapper<RolePermission>()
                            .eq(RolePermission::getRoleUid, roleUid));
            if (rolePermissionList == null) {
                return permissionList;
            }
            for (RolePermission rolePermission : rolePermissionList) {
                Permission permission = permissionDao.selectById(rolePermission.getPermissionUid());
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


        List<String> roleNameByMemberUid = roleDao.getRoleNameByMemberUid((Long) loginId);

        redisUtil.set(RedisKeyConstant.ROLE_LIST_KEY + loginId, roleNameByMemberUid);

        return roleNameByMemberUid;
    }
}
