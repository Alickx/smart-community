package cn.goroute.smart.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.ErrorCodeEnum;
import cn.goroute.smart.common.model.dto.UserProfileDTO;
import cn.goroute.smart.user.converter.UserProfileConverter;
import cn.goroute.smart.user.domain.UserProfile;
import cn.goroute.smart.user.manager.UserProfileManager;
import cn.goroute.smart.user.mapper.UserProfileMapper;
import cn.goroute.smart.user.service.UserProfileService;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.redis.core.annotation.Cached;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【user_profile(用户信息表)】的数据库操作Service实现
* @createDate 2022-09-17 19:30:44
*/
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl extends ExtendServiceImpl<UserProfileMapper, UserProfile>
    implements UserProfileService{

    private final UserProfileMapper userProfileMapper;

    private final UserProfileManager userProfileManager;

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @Override
    @Cached(key = "user:profile",keyJoint = "#userId")
    public R<UserProfileDTO> getUserProfile(Long userId) {

        if (userId == null) {
            return R.failed(ErrorCodeEnum.USER_NOT_LOGIN);
        }

        UserProfile userProfile = userProfileMapper
                .selectOne(new LambdaQueryWrapper<UserProfile>().eq(UserProfile::getUserId, userId));
        UserProfileDTO userProfileDto = UserProfileConverter.INSTANCE.poToDto(userProfile);

        return R.ok(userProfileDto);
    }

    /**
     * 初始化用户信息
     *
     * @param userProfileDto 用户信息
     * @return 是否成功
     */
    @Override
    public R<Boolean> initUserProfile(UserProfileDTO userProfileDto) {

        UserProfile userProfile = Convert
                .convert(UserProfile.class, userProfileDto);
		//TODO 完善初始化用户信息
        userProfile.setAvatar("https://img.llwstu.com/img/202208212352490.png");
        userProfile.setNickName("用户" + StpUtil.getLoginIdAsLong());
        userProfileManager.initUserProfile(userProfile);
        return R.ok();
    }

	/**
	 * 批量获取用户信息
	 *
	 * @param userIds 用户id集合
	 * @return 用户信息集合
	 */
	@Override
	public R<List<UserProfileDTO>> batchGetUserProfile(List<Long> userIds) {

		List<UserProfile> userProfiles = userProfileMapper
				.selectList(new LambdaQueryWrapper<UserProfile>()
						.in(UserProfile::getUserId, userIds));

		List<UserProfileDTO> result = UserProfileConverter.INSTANCE.poToDto(userProfiles);

		return R.ok(result);

	}


}




