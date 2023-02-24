package cn.goroute.smart.user.modules.profile.manager;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.user.constant.RedisConstant;
import cn.goroute.smart.user.domain.entity.UserProfileEntity;
import cn.goroute.smart.user.modules.profile.mapper.UserProfileMapper;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


	public UserProfileEntity getUserProfile(Long userId) {

		String userProfileKey = RedisConstant.USER_PROFILE + ":" + userId;

		String userProfileJson = redisUtil.get(userProfileKey);

		if (StrUtil.isNotBlank(userProfileJson)) {
			return JSON.parseObject(userProfileJson, UserProfileEntity.class);
		}

		UserProfileEntity userProfileEntity = userProfileMapper
				.selectOne(new LambdaQueryWrapper<UserProfileEntity>().eq(UserProfileEntity::getUserId, userId));

		if (null != userProfileEntity) {
			redisUtil.setEx(userProfileKey, JSON.toJSONString(userProfileEntity), USER_PROFILE_EXPIRE_TIME, TimeUnit.SECONDS);
		}

		return userProfileEntity;
	}

	public List<UserProfileEntity> batchGetUserProfile(List<Long> userIds) {

		List<UserProfileEntity> ret = new ArrayList<>(userIds.size());

		List<Long> unCachedUserIds = new ArrayList<>();
		for (Long userId : userIds) {

			String userProfileKey = RedisConstant.USER_PROFILE + ":" + userId;

			String userProfileJson = redisUtil.get(userProfileKey);

			// 如果缓存中存在用户资料，则直接添加进返回列表
			if (StrUtil.isNotBlank(userProfileJson)) {
				ret.add(JSON.parseObject(userProfileJson, UserProfileEntity.class));
			} else {
				unCachedUserIds.add(userId);
			}
		}

		// 从数据库中查询未缓存的用户资料
		if (CollUtil.isNotEmpty(unCachedUserIds)) {
			List<UserProfileEntity> userProfileEntities = userProfileMapper
					.selectList(new LambdaQueryWrapper<UserProfileEntity>().in(UserProfileEntity::getUserId, unCachedUserIds));

			for (UserProfileEntity userProfileEntity : userProfileEntities) {
				String userProfileKey = RedisConstant.USER_PROFILE + ":" + userProfileEntity.getUserId();
				redisUtil.setEx(userProfileKey, JSON.toJSONString(userProfileEntity), USER_PROFILE_EXPIRE_TIME,
						TimeUnit.SECONDS);
			}

			ret.addAll(userProfileEntities);
		}

		return ret;
	}
}
