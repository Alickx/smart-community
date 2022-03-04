package cn.goroute.smart.gateway.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.goroute.smart.common.dao.PermissionDao;
import cn.goroute.smart.common.dao.RoleDao;
import cn.goroute.smart.common.dao.RolePermissionDao;
import cn.goroute.smart.common.dao.UserRoleDao;
import cn.goroute.smart.common.entity.PermissionEntity;
import cn.goroute.smart.common.entity.RolePermissionEntity;
import cn.goroute.smart.common.entity.UserRoleEntity;
import cn.goroute.smart.common.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cn.goroute.smart.common.utils.Constant.PERMISSION_LIST_KEY;
import static cn.goroute.smart.common.utils.Constant.ROLE_LIST_KEY;

@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface{

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

        //先查看缓存
        if (redisUtil.hasKey(PERMISSION_LIST_KEY + loginId)){
            permissionList = (List<String>) redisUtil.get(PERMISSION_LIST_KEY + loginId);
            return permissionList;
        }

        List<UserRoleEntity> userRoleList = userRoleDao.selectList(new QueryWrapper<UserRoleEntity>()
                .eq("user_uid", loginId));
        if (userRoleList == null) return permissionList;
        for (UserRoleEntity userRoleEntity : userRoleList) {
            int roleUid = userRoleEntity.getRoleUid();
            List<RolePermissionEntity> rolePermissionEntityList = rolePermissionDao
                    .selectList(new QueryWrapper<RolePermissionEntity>()
                            .eq("role_uid", roleUid));
            if (rolePermissionEntityList == null) return permissionList;
            for (RolePermissionEntity rolePermissionEntity : rolePermissionEntityList) {
                PermissionEntity permissionEntity = permissionDao.selectById(rolePermissionEntity.getPermissionUid());
                if (permissionEntity != null) {
                    permissionList.add(permissionEntity.getPermissionName());
                }
            }
        }

        redisUtil.set(PERMISSION_LIST_KEY+loginId,permissionList);

        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        List<String> roleList;

        if (redisUtil.hasKey(ROLE_LIST_KEY + loginId)) {
            roleList = (List<String>) redisUtil.get(ROLE_LIST_KEY + loginId);
            return roleList;
        }

        roleList = roleDao.getRoleNameByMemberUid((String) loginId);

        redisUtil.set(ROLE_LIST_KEY + loginId,roleList);

        return roleList;
    }
}
