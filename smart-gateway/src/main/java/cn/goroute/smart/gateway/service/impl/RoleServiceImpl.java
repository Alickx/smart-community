package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.gateway.mapper.RoleMapper;
import cn.goroute.smart.gateway.entity.pojo.Role;
import cn.goroute.smart.gateway.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


}