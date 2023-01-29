package cn.goroute.smart.notify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Alickx
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.goroute.smart.*.feign"})
@EnableAsync
@MapperScan("cn.goroute.smart.*.mapper")
public class SmartNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartNotifyApplication.class, args);
    }

}
