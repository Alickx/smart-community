package cn.goroute.smart.auth.module.login.mapper;

import cn.goroute.smart.auth.domain.entity.RolePermissionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【role_permission】的数据库操作Mapper
* @createDate 2022-09-17 19:21:38
* @Entity cn.goroute.smart.auth.domain.entity.RolePermission
*/
public interface RolePermissionMapper extends BaseMapper<RolePermissionEntity> {

    /**
     * 根据角色id查询权限
     * @param roleIds 角色id
     * @return 权限列表
     */
    List<String> selectByRoleIds(List<Long> roleIds);
}




