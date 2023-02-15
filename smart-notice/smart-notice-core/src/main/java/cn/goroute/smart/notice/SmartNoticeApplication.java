package cn.goroute.smart.notice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@MapperScan("cn.goroute.smart.*.mapper")
@EnableFeignClients(basePackages = {"cn.goroute.smart.*.feign"})
@EnableAsync
@EnableTransactionManagement
@EnableOpenApi
public class SmartNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartNoticeApplication.class, args);
    }

}
