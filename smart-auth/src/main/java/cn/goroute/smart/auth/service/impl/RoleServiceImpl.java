package cn.goroute.smart.auth.service.impl;

import cn.goroute.smart.auth.domain.Role;
import cn.goroute.smart.auth.mapper.RoleMapper;
import cn.goroute.smart.auth.service.RoleService;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【role】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
public class RoleServiceImpl extends ExtendServiceImpl<RoleMapper, Role>
    implements RoleService{

    /**
     * 获取角色
     * @return 角色列表
     */
    @Override
    public R<List<String>> getRole() {
        return null;
    }
}




