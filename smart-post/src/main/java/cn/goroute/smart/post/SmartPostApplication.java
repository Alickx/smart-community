package cn.goroute.smart.post;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@RefreshScope
@MapperScan("cn.goroute.smart.common.dao")
@EnableFeignClients(basePackages = {"cn.goroute.smart.post.feign","cn.goroute.smart.common.feign"})
public class SmartPostApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartPostApplication.class, args);
    }

}
