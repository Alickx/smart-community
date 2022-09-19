package cn.goroute.smart.auth;

import com.hccake.ballcat.common.swagger.annotation.EnableSwagger2Provider;
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
public class SmartAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAuthApplication.class, args);
    }

}
