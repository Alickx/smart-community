package cn.goroute.smart.thirdpart.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/19/10:37
 * @Description: 三方服务全局过滤器
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")
                .setAuth(obj -> {
                    SaSameUtil.checkCurrentRequestToken();
                })
                .setError(e -> SaResult.error(e.getMessage()))
                ;
    }
}
