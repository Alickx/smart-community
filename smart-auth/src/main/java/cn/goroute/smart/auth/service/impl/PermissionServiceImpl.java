package cn.goroute.smart.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.auth.domain.Permission;
import cn.goroute.smart.auth.service.PermissionService;
import cn.goroute.smart.auth.mapper.PermissionMapper;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【permission】的数据库操作Service实现
* @createDate 2022-09-17 19:21:38
*/
@Service
public class PermissionServiceImpl extends ExtendServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




