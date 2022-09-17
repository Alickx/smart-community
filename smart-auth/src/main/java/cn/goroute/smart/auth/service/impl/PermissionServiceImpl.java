package cn.goroute.smart.auth.service.impl;

import cn.goroute.smart.auth.domain.Permission;
import cn.goroute.smart.auth.mapper.PermissionMapper;
import cn.goroute.smart.auth.service.PermissionService;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【permission】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
public class PermissionServiceImpl extends ExtendServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    /**
     * 获取权限
     * @return 权限列表
     */
    @Override
    public R<List<String>> getPermission() {
        return null;
    }
}




