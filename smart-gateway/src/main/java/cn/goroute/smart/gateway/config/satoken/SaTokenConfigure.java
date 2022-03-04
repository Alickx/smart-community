package cn.goroute.smart.gateway.config.satoken;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.goroute.smart.common.utils.R;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfigure {
    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/admin")
                // 开放地址
                .addExclude("/favicon.ico")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
//                    SaRouter.match("/**", "/login", r -> StpUtil.checkLogin());

//                        SaRouter.match("/api/v1/post/**", r -> StpUtil.checkRole("ROLE_A"));

                    // ...
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> R.error(e.getMessage()))
                ;
    }
}

