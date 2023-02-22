package cn.goroute.smart.common.config;

import cn.goroute.smart.common.modules.trace.TraceIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author: Alickx
 * @Date: 2023/02/11 11:26:23
 * @Description:
 */
@Configuration
public class TraceIdFilterConfig {

	@Bean
	public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistrationBean() {
		FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>(new TraceIdFilter());
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}

}
