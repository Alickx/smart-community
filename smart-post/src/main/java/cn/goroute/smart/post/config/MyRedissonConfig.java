package cn.goroute.smart.post.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Alickx
 * @Date: 2022/04/02/10:55
 * @Description: Redisson配置类
 */
@Configuration
public class MyRedissonConfig {

	// 服务停止后调用 shutdown 方法。
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
				.setAddress("redis://124.223.80.135:6379")
				.setPassword("ak87715700");

        return Redisson.create(config);
    }
}
