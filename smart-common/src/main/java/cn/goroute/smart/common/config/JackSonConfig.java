package cn.goroute.smart.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/11:53
 * @Description: 公有jackson配置
 */
@Configuration
public class JackSonConfig {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		// 取消默认转换timestamps形式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 忽略空bean转json的错误
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		//设置时区为东八区
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		// 统一日期格式
		objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
		// 反序列化时,忽略在json字符串中存在, 但在java对象中不存在对应属性的情况, 防止错误
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 序列换成json时,将所有的long变成string
		objectMapper.registerModule(new SimpleModule().addSerializer(Long.class, ToStringSerializer.instance).addSerializer(Long.TYPE, ToStringSerializer.instance));
		objectMapper.registerModule(new JavaTimeModule());
		// 所有字段都可见
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		return objectMapper;
	}

}
