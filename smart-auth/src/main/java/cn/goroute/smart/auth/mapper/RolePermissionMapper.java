package cn.goroute.smart.auth.mapper;

import cn.goroute.smart.auth.domain.RolePermission;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【role_permission】的数据库操作Mapper
* @createDate 2022-09-17 19:21:38
* @Entity cn.goroute.smart.auth.domain.RolePermission
*/
public interface RolePermissionMapper extends ExtendMapper<RolePermission> {

    /**
     * 根据角色id查询权限
     * @param roleIds 角色id
     * @return 权限列表
     */
    List<String> selectByRoleIds(List<Long> roleIds);
}




