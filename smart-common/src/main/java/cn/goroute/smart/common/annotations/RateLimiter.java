package cn.goroute.smart.common.annotations;

import cn.goroute.smart.common.constant.LimitType;

import java.lang.annotation.*;

/**
 * @Author: Alickx
 * @Date: 2022/06/17/15:10
 * @Description: 限流注解
 *
 * 两个方向进行限流
 * 1. 当前接口的全局性限流
 * 2. 针对一个IP地址的限流
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    /**
     * 限流key
     */
    String key() default "rate_limit";

    /**
     * 窗口大小 单位:秒
     */
    int time() default 60;

    /**
     * 窗口通过总次数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;

}
