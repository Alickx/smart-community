package cn.goroute.smart.post;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication(scanBasePackages = {"cn.goroute"})
@RefreshScope
@MapperScan("cn.goroute.smart.post.dao")
public class SmartPostApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartPostApplication.class, args);
    }

}
