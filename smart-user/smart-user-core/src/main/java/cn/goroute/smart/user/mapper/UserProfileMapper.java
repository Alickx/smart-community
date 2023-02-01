package cn.goroute.smart.user.mapper;

import cn.goroute.smart.user.domain.UserProfile;
import com.hccake.extend.mybatis.plus.conditions.query.LambdaQueryWrapperX;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;

/**
 * @author caiguopeng
 * @description 针对表【user_profile(用户信息表)】的数据库操作Mapper
 * @createDate 2022-09-17 19:30:44
 * @Entity cn.goroute.smart.user.domain.UserProfile
 */
public interface UserProfileMapper extends ExtendMapper<UserProfile> {

	default void updateByUserId(UserProfile userProfile, long userId) {
		LambdaQueryWrapperX<UserProfile> wrapper = new LambdaQueryWrapperX<>();
		wrapper.eq(UserProfile::getUserId, userId);
		update(userProfile, wrapper);
	}
}




