package cn.goroute.smart.thirdpart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;


@MapperScan("cn.goroute.smart.common.dao")
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableFeignClients("cn.goroute.smart.thirdpart.feign")
public class SmartThirdpartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartThirdpartApplication.class, args);
    }

}
