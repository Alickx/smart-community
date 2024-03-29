package cn.goroute.smart.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = "cn.goroute.smart")
@EnableDiscoveryClient
@MapperScan("cn.goroute.smart.*.module.*.mapper")
@EnableAsync
@EnableFeignClients(basePackages = "cn.goroute.smart.*.feign")
public class SmartAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAuthApplication.class, args);
    }

}
