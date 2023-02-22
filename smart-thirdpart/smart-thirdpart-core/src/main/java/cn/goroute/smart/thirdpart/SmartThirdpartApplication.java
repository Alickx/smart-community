package cn.goroute.smart.thirdpart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: Alickx
 * @Date: 2023/02/21 11:19:34
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@EnableAsync
public class SmartThirdpartApplication {

	public static void main(String[] args) {

		SpringApplication.run(SmartThirdpartApplication.class, args);
	}

}
