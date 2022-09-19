package cn.goroute.smart.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = "cn.goroute.smart")
@EnableDiscoveryClient
@MapperScan("cn.goroute.smart.*.mapper")
@EnableFeignClients
@EnableOpenApi
@EnableWebMvc
public class SmartAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAuthApplication.class, args);
    }

}
