package cn.goroute.smart.auth.service;

import cn.goroute.smart.auth.domain.AuthUser;
import cn.goroute.smart.auth.mapper.AuthUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/19:32
 * @Description:
 */

@SpringBootTest
public class AuthUserServiceTest {

    @Resource
    private AuthUserMapper authUserMapper;

    @Test
    public void test(){
        AuthUser authUser = new AuthUser();
        authUser.setIdentityType(0);
        authUser.setIdentifier("132");
        authUser.setCertificate("122132");
        authUserMapper.insert(authUser);
    }


}
