package cn.goroute.smart.auth.module.login.service;

import cn.goroute.smart.auth.domain.entity.RoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.goroute.smart.common.modules.result.R;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【role】的数据库操作Service
* @createDate 2022-09-17 19:21:38
*/
public interface RoleService extends IService<RoleEntity> {

    /**
     * 获取角色
     * @return 角色列表
     */
    R<List<String>> getRole(Long userId);
}
