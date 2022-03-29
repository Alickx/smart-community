package cn.goroute.smart.notify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@RefreshScope
@EnableFeignClients("cn.goroute.smart.notify.feign")
@MapperScan("cn.goroute.smart.common.dao")
public class SmartNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartNotifyApplication.class, args);
    }

}
