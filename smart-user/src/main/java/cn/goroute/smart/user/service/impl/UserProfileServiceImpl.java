package cn.goroute.smart.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.ErrorCodeEnum;
import cn.goroute.smart.common.entity.dto.UserProfileDto;
import cn.goroute.smart.user.domain.UserProfile;
import cn.goroute.smart.user.manager.UserProfileManager;
import cn.goroute.smart.user.mapper.UserProfileMapper;
import cn.goroute.smart.user.service.UserProfileService;
import cn.hutool.core.convert.Convert;
import cn.hutool.http.useragent.Browser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.redis.core.annotation.CachePut;
import com.hccake.ballcat.common.redis.core.annotation.Cached;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    @CachePut(key = "user:profile",keyJoint = "#userId", ttl = 120)
    @Cached(key = "user:profile",keyJoint = "#userId")
    public R<UserProfileDto> getUserProfile(Long userId) {

        if (userId == null) {
            return R.failed(ErrorCodeEnum.USER_NOT_LOGIN);
        }

        UserProfile userProfile = userProfileMapper
                .selectOne(new LambdaQueryWrapper<UserProfile>().eq(UserProfile::getUserId, userId));
        UserProfileDto userProfileDto = Convert
                .convert(UserProfileDto.class, userProfile);

        return R.ok(userProfileDto);
    }

    /**
     * 初始化用户信息
     *
     * @param userProfileDto 用户信息
     * @return 是否成功
     */
    @Override
    public R<Boolean> initUserProfile(UserProfileDto userProfileDto) {

        UserProfile userProfile = Convert
                .convert(UserProfile.class, userProfileDto);

        userProfile.setAvatar("https://img.llwstu.com/img/202208212352490.png");
        userProfile.setNickName("用户" + StpUtil.getLoginIdAsLong());

        userProfileManager.initUserProfile(userProfile);

        return R.ok();
    }



}




