package cn.goroute.smart.user;

import com.hccake.ballcat.common.job.annotation.EnableXxlJob;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("cn.goroute.smart.*.mapper")
@EnableXxlJob
@EnableOpenApi
public class SmartUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartUserApplication.class, args);
    }

}
