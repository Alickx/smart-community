package cn.goroute.smart.notify.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Alickx
 */
@Configuration
public class DruidConfig {

    @PostConstruct
    public void setProperties(){
        System.setProperty("druid.mysql.usePingMethod","false");
    }

}
