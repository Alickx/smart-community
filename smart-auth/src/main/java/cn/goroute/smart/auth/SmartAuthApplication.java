package cn.goroute.smart.auth;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = "cn.goroute.smart")
@EnableDiscoveryClient
@MapperScan("cn.goroute.smart.*.mapper")
@RefreshScope
@EnableFeignClients("cn.goroute.smart.common.feign")
@Slf4j
public class SmartAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAuthApplication.class, args);

        log.info("智慧社区认证服务启动成功,端口:{},文档地址:{}",
                "17000",
                "http://localhost:17000/swagger-ui/index.htm");

    }

}
