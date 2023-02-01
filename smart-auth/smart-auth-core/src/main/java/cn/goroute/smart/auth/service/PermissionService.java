package cn.goroute.smart.auth.service;

import cn.goroute.smart.auth.domain.Permission;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【permission】的数据库操作Service
* @createDate 2022-09-17 19:21:38
*/
public interface PermissionService extends ExtendService<Permission> {

    /**
     * 获取权限
     * @return 权限列表
     */
    R<List<String>> getPermission(Long userId);
}
