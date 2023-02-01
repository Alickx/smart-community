package cn.goroute.smart.user.manager;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.user.constant.RedisConstant;
import cn.goroute.smart.user.domain.UserProfile;
import cn.goroute.smart.user.mapper.UserProfileMapper;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/24/11:55
 * @Description: 用户资料manager层
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileManager {

	private final UserProfileMapper userProfileMapper;

	private final RedisUtil redisUtil;

	/**
	 * 用户资料缓存过期时间
	 */
	private final int USER_PROFILE_EXPIRE_TIME = 60 * 60 * 24 * 7;

	@Transactional(rollbackFor = Exception.class)
	public void initUserProfile(UserProfile userProfile) {
		userProfileMapper.insert(userProfile);
	}

	public UserProfile getUserProfile(Long userId) {

		String userProfileKey = RedisConstant.USER_PROFILE + ":" + userId;

		String userProfileJson = redisUtil.get(userProfileKey);

		if (StrUtil.isNotBlank(userProfileJson)) {
			return JsonUtils.toObj(userProfileJson, UserProfile.class);
		}

		UserProfile userProfile = userProfileMapper
				.selectOne(new LambdaQueryWrapper<UserProfile>().eq(UserProfile::getUserId, userId));

		if (null != userProfile) {
			redisUtil.setEx(userProfileKey, JsonUtils.toJson(userProfile), USER_PROFILE_EXPIRE_TIME, TimeUnit.SECONDS);
		}

		return userProfile;
	}

	public List<UserProfile> batchGetUserProfile(List<Long> userIds) {

		List<UserProfile> ret = new ArrayList<>(userIds.size());

		for (Long userId : userIds) {
			UserProfile userProfile = getUserProfile(userId);
			if (null != userProfile) {
				ret.add(userProfile);
			}
		}

		return ret;
	}
}
