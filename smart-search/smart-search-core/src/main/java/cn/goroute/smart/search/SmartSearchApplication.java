package cn.goroute.smart.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Alickx
 */
@SpringBootApplication(scanBasePackages = {"cn.goroute.smart"})
@EnableDiscoveryClient
@RefreshScope
@EnableAsync
@EnableElasticsearchRepositories(basePackages = "cn.goroute.smart.search.mapper")
@EnableFeignClients("cn.goroute.smart.*.feign")
public class SmartSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSearchApplication.class, args);
    }

}
