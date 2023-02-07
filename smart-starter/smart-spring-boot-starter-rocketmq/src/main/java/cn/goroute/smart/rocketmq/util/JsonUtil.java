package cn.goroute.smart.rocketmq.util;


import com.alibaba.fastjson2.JSON;

/**
 * JSON工具类
 */
public class JsonUtil {
    private JsonUtil() {}

    public static String toJson(Object value) {
		return JSON.toJSONString(value);
    }

    public static <T> T toObject(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }
}
