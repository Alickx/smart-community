package cn.goroute.smart.auth.strategy.register;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/17:37
 * @Description: 注册策略工厂
 */
@Component
public class RegisterStrategyFactory {

    @Bean
    public RegisterStrategy registerStrategy(Map<String, IRegister> registerMap) {
        return new RegisterStrategy(registerMap);
    }

}
