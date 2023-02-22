package cn.goroute.smart.auth.module.login.mapper;

import cn.goroute.smart.auth.domain.entity.AuthUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author caiguopeng
* @description 针对表【auth_user(用户授权表)】的数据库操作Mapper
* @createDate 2022-09-17 19:21:38
* @Entity cn.goroute.smart.auth.domain.entity.AuthUser
*/
public interface AuthUserMapper extends BaseMapper<AuthUserEntity> {

    AuthUserEntity selectByUserName(@Param("username") String username);

	AuthUserEntity selectByUserEmail(@Param("userEmail") String userEmail);

}




