package cn.goroute.smart.gateway.service.impl;

import cn.goroute.smart.common.dao.RoleDao;
import cn.goroute.smart.common.entity.pojo.Role;
import cn.goroute.smart.gateway.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {


}