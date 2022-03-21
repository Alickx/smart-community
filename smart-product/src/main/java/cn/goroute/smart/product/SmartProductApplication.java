package cn.goroute.smart.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableFeignClients("cn.goroute.smart.product.feign")
@MapperScan("cn.goroute.smart.common.dao")
@EnableDiscoveryClient
@RefreshScope
public class SmartProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartProductApplication.class, args);
    }

}
