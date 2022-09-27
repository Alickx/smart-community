package cn.goroute.smart.user.service;

import cn.goroute.smart.common.entity.dto.UserProfileDTO;
import cn.goroute.smart.user.domain.UserProfile;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【user_profile(用户信息表)】的数据库操作Service
* @createDate 2022-09-17 19:30:44
*/
public interface UserProfileService extends ExtendService<UserProfile> {

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    R<UserProfileDTO> getUserProfile(Long userId);

    /**
     * 初始化用户信息
     * @param userProfileDto 用户信息
     * @return 是否成功
     */
    R<Boolean> initUserProfile(UserProfileDTO userProfileDto);

	/**
	 * 批量获取用户信息
	 * @param userIds 用户id集合
	 * @return 用户信息集合
	 */
    R<List<UserProfileDTO>> batchGetUserProfile(List<Long> userIds);
}
