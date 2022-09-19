package cn.goroute.smart.user;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("cn.goroute.smart.*.mapper")
@EnableOpenApi
@EnableWebMvc
@Slf4j
public class SmartUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartUserApplication.class, args);
    }

}
