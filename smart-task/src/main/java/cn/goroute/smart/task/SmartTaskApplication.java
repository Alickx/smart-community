package cn.goroute.smart.task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication(scanBasePackages = "cn.goroute")
@MapperScan("cn.goroute.smart.common.dao")
@MapperScan("cn.goroute.smart.task.dao")
@EnableDiscoveryClient
@RefreshScope
public class SmartTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartTaskApplication.class, args);
    }

}
