package cn.goroute.smart.post;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Alickx
 * @Date: 2022/07/09 11:13
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@MapperScan("cn.goroute.smart.*.modules.*.mapper")
@EnableFeignClients(basePackages = {"cn.goroute.smart.*.feign"})
@EnableAsync
public class SmartPostApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartPostApplication.class, args);
    }


}
