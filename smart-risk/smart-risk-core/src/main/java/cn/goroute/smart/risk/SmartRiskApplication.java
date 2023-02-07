package cn.goroute.smart.risk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author: Alickx
 * @Date: 2023/02/04 22:31:51
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.goroute.smart.*.feign"})
@EnableAsync
@EnableOpenApi
public class SmartRiskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartRiskApplication.class, args);
	}
}
