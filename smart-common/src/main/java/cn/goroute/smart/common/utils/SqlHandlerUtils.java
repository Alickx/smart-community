package cn.goroute.smart.common.utils;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.annotation.TableField;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/15:45
 * @Description:
 */
public class SqlHandlerUtils {

    /**
     * 获取所有key:
     * 配置所有key: k: 默认由 实例的字段 转为驼峰命名方式 / @TableField 指定   --->过滤 @TableField(exist = false) | v is null
     * @param instance
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Map<String, Object> getInstanceKeyValue(T instance) throws IllegalAccessException {
        // 存储实例中进行校验的 k v
        HashMap<String, Object> map = new HashMap<>();
        if (instance != null) {
            Field[] instanceFields = instance.getClass().getDeclaredFields();
            for (Field field : instanceFields) {
                // static | final 不操作
                if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object o = field.get(instance);
                if (o == null) {
                    continue;
                }
                boolean b = field.isAnnotationPresent(TableField.class);
                // 如果存在该注解
                if (b) {
                    TableField annotationInstance = field.getAnnotation(TableField.class);
                    boolean isExist = annotationInstance.exist();
                    if (!isExist) {
                        continue;
                    }
                    String value = annotationInstance.value();
                    if (CharSequenceUtil.isNotBlank(value)) {
                        map.put(value, o);
                        continue;
                    }
                }
                // 执行驼峰转下划线
                map.put(CharSequenceUtil.toUnderlineCase(field.getName()), o);
            }
        }
        return map;
    }

}
