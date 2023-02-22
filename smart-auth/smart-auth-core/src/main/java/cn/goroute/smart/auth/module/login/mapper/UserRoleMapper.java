package cn.goroute.smart.auth.module.login.mapper;

import cn.goroute.smart.auth.domain.entity.UserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2022-09-17 19:21:38
* @Entity cn.goroute.smart.auth.domain.entity.UserRole
*/
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

    List<Long> selectByUserId(long userId);
}




