package cn.goroute.smart.admin.config;

import com.yomahub.tlog.web.interceptor.TLogWebInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Alickx
 * @Date: 2023/02/24 11:10:56
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TLogWebInterceptor());
	}
}
