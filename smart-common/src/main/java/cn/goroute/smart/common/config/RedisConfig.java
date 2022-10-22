package cn.goroute.smart.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/11:17
 * @Description: 公有redis配置
 */
public class RedisConfig {

	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		//初始化一个redis模板
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		//使用fastjson实现对于对象得序列化
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(om);

		//设置“值”的序列化方式
		template.setValueSerializer(serializer);
		//设置“hash”类型数据的序列化方式
		template.setHashValueSerializer(serializer);
		//设置“key"的序列化方式
		template.setKeySerializer(new StringRedisSerializer());
		//设置“hash的key”的序列化方式
		template.setHashKeySerializer(new StringRedisSerializer());
		//设置redis模板的工厂对象
		template.setConnectionFactory(redisConnectionFactory);

		return template;
	}

	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

		StringRedisTemplate template = new StringRedisTemplate();

		template.setConnectionFactory(redisConnectionFactory);

		return template;
	}


}
