package cn.goroute.smart.auth.mapper;

import cn.goroute.smart.auth.domain.UserRole;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2022-09-17 19:21:38
* @Entity cn.goroute.smart.auth.domain.UserRole
*/
public interface UserRoleMapper extends ExtendMapper<UserRole> {

    List<Long> selectByUserId(long userId);
}



