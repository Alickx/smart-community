package cn.goroute.smart.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("cn.goroute.smart.*.mapper")
@EnableAsync
public class SmartUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartUserApplication.class, args);
    }

}
