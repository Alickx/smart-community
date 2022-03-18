package cn.goroute.smart.common.service.impl;

import cn.goroute.smart.common.dao.RoleDao;
import cn.goroute.smart.common.entity.pojo.RoleEntity;
import cn.goroute.smart.common.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {


}