package cn.goroute.smart.common.config;

import cn.dev33.satoken.id.SaIdUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/19/10:44
 * @Description: Feign全局拦截器
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(SaIdUtil.ID_TOKEN, SaIdUtil.getToken());
    }
}
