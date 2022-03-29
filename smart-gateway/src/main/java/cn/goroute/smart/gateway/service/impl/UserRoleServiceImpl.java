package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.common.dao.UserRoleDao;
import cn.goroute.smart.common.entity.pojo.UserRole;
import cn.goroute.smart.gateway.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {


}