package cn.goroute.smart.common.serialize;

import com.alibaba.fastjson2.JSON;
import com.hccake.ballcat.common.redis.serialize.CacheSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;


@Component
public class FastJson2Serializer implements CacheSerializer {


	/**
	 * 反序列化方法
	 * @param cacheData 缓存中的数据
	 * @param type 反序列化目标类型
	 * @return 反序列化后的对象
	 */
	@Override
	public Object deserialize(String cacheData, Type type) {
		return JSON.parseObject(cacheData, type);
	}

	/**
	 * 序列化方法
	 * @param cacheData 待缓存的数据
	 * @return 序列化后的数据
	 * @throws IOException IO异常
	 */
	@Override
	public String serialize(Object cacheData) throws IOException {
		return JSON.toJSONString(cacheData);
	}

}
