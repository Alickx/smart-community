package cn.goroute.smart.common.config;

import cn.dev33.satoken.same.SaSameUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/19/10:44
 * @Description: Feign全局拦截器
 */
@Component
@Slf4j
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken());
	}
}
