package cn.goroute.smart.user.manager;

import cn.goroute.smart.user.domain.UserProfile;
import cn.goroute.smart.user.mapper.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/24/11:55
 * @Description: 用户资料manager层
 */
@Service
@RequiredArgsConstructor
public class UserProfileManager {

    private final UserProfileMapper userProfileMapper;

    /**
     * 初始化用户信息
     * @param userProfile 用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void initUserProfile(UserProfile userProfile){
        userProfileMapper.insert(userProfile);
    }

}
