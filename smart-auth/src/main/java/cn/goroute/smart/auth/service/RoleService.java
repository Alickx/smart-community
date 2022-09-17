package cn.goroute.smart.auth.service;

import cn.goroute.smart.auth.domain.Role;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【role】的数据库操作Service
* @createDate 2022-09-17 19:21:38
*/
public interface RoleService extends ExtendService<Role> {

    R<List<String>> getRole();
}
