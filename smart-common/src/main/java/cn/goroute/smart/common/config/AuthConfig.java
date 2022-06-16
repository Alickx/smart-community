package cn.goroute.smart.common.config;

import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.service.impl.AuthSatokenServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Alickx
 * @Date: 2022/06/12/15:32
 * @Description:
 */
@Configuration
public class AuthConfig {
    @Bean
    public AuthService authService() {
        return new AuthSatokenServiceImpl();
    }
}
