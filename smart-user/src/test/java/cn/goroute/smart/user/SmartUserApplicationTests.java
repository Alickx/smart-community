package cn.goroute.smart.user;

import cn.goroute.smart.user.domain.UserProfile;
import cn.goroute.smart.user.mapper.UserProfileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SmartUserApplicationTests {

    @Resource
    UserProfileMapper userProfileMapper;

    @Test
    void contextLoads() {

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(10123123123L);
        userProfileMapper.insert(userProfile);

    }

}
