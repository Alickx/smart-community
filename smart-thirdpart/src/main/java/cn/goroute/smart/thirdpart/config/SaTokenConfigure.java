package cn.goroute.smart.thirdpart.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.id.SaIdUtil;
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
                    // 校验 Id-Token 身份凭证     —— 以下两句代码可简化为：SaIdUtil.checkCurrentRequestToken();
                    String token = SaHolder.getRequest().getHeader(SaIdUtil.ID_TOKEN);
                    SaIdUtil.checkToken(token);
                })
                .setError(e -> SaResult.error(e.getMessage()))
                ;
    }
}
