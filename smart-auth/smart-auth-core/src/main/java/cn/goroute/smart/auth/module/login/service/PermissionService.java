package cn.goroute.smart.auth.module.login.service;

import cn.goroute.smart.auth.domain.entity.PermissionEntity;
import cn.goroute.smart.common.modules.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【permission】的数据库操作Service
* @createDate 2022-09-17 19:21:38
*/
public interface PermissionService extends IService<PermissionEntity> {

    /**
     * 获取权限
     * @return 权限列表
     */
    R<List<String>> getPermission(Long userId);
}
