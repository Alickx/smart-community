package cn.goroute.smart.user.service;

import cn.goroute.smart.common.entity.dto.UserProfileDto;
import cn.goroute.smart.user.domain.UserProfile;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

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
    R<UserProfileDto> getUserProfile(Long userId);

    /**
     * 初始化用户信息
     * @param userProfileDto 用户信息
     * @return 是否成功
     */
    R<Boolean> initUserProfile(UserProfileDto userProfileDto);
}
