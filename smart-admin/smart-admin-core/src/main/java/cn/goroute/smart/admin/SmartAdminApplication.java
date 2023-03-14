package cn.goroute.smart.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Alickx
 * @Date: 2023/03/12/23:57
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("cn.goroute.smart.*.module.*.mapper")
public class SmartAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartAdminApplication.class, args);
	}

}
