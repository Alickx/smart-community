package cn.goroute.smart.user.modules.profile.service;

import cn.goroute.smart.auth.domain.dto.AuthUserDTO;
import cn.goroute.smart.post.domain.dto.PostPublicEventDTO;
import cn.goroute.smart.user.domain.entity.UserProfileEntity;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.goroute.smart.common.modules.result.R;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【user_profile(用户信息表)】的数据库操作Service
* @createDate 2022-09-17 19:30:44
*/
public interface UserProfileService extends IService<UserProfileEntity> {

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    R<UserProfileVO> getUserProfile(Long userId);

    /**
     * 初始化用户信息
     * @param authUserDTO 用户信息
     * @return 是否成功
     */
    R<Boolean> initUserProfile(AuthUserDTO authUserDTO);

	/**
	 * 批量获取用户信息
	 * @param userIds 用户id集合
	 * @return 用户信息集合
	 */
    R<List<UserProfileVO>> batchGetUserProfile(List<Long> userIds);

	/**
	 * 更新用户信息
	 * @param userProfileVO 用户信息vo
	 * @return 是否成功
	 */
	R<Boolean> updateUserProfile(UserProfileVO userProfileVO);

	/**
	 * 用户发布文章后的处理
	 */
	void postPublicEventHandle(PostPublicEventDTO postPublicEventDTO);
}
