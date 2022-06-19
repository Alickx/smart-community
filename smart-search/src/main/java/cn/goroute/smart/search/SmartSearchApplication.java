package cn.goroute.smart.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@RefreshScope
@EnableFeignClients("cn.goroute.smart.search.feign")
public class SmartSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSearchApplication.class, args);
    }

}
