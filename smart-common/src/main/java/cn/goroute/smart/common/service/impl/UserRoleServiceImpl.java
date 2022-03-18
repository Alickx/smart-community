package cn.goroute.smart.common.service.impl;

import cn.goroute.smart.common.dao.UserRoleDao;
import cn.goroute.smart.common.entity.pojo.UserRoleEntity;
import cn.goroute.smart.common.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleEntity> implements UserRoleService {


}