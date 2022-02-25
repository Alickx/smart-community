package cn.goroute.smart.comment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
@MapperScan("cn.goroute.smart.comment.dao")
public class SmartCommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCommentApplication.class, args);
    }

}
