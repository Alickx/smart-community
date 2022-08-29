package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.gateway.mapper.UserRoleMapper;
import cn.goroute.smart.gateway.entity.pojo.UserRole;
import cn.goroute.smart.gateway.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {


}