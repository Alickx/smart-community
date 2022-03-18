package cn.goroute.smart.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@EnableFeignClients("cn.goroute.smart.member.feign")
@MapperScan("cn.goroute.smart.common.dao")
@RefreshScope
public class SmartMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartMemberApplication.class, args);
    }

}
