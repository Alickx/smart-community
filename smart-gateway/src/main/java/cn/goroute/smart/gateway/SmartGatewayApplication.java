package cn.goroute.smart.gateway;


import com.hccake.ballcat.common.swagger.annotation.EnableSwagger2Aggregator;
import com.hccake.ballcat.common.swagger.annotation.EnableSwagger2Provider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.reactive.config.EnableWebFlux;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Alickx
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableOpenApi
public class SmartGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartGatewayApplication.class, args);
    }

}
