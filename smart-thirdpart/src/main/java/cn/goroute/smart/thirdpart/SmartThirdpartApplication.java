package cn.goroute.smart.thirdpart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@EnableFeignClients("cn.goroute.smart.common.feign")
@RefreshScope
public class SmartThirdpartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartThirdpartApplication.class, args);
    }





}
