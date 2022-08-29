package cn.goroute.smart.notify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients("cn.goroute.smart.common.feign")
@MapperScan("cn.goroute.smart.*.mapper")
public class SmartNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartNotifyApplication.class, args);
    }

}
