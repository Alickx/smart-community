package cn.goroute.smart.common.aspect;

import cn.goroute.smart.common.annotations.RateLimiter;
import cn.goroute.smart.common.constant.LimitType;
import cn.goroute.smart.common.exception.ServiceException;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/06/17/15:40
 * @Description: 限流注解
 */
@Aspect
@Component
@Slf4j
public class RateLimiterAspect {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    RedisScript<Long> limitScript;

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) {

        String key = rateLimiter.key();
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(rateLimiter, point);
        List<Object> keys = Collections.singletonList(combineKey);

        try {
            Long number = redisTemplate.execute(limitScript, keys, time, System.currentTimeMillis());
            if (number == null || number.intValue() > count) {
                throw new ServiceException("访问过于频繁，请稍后再试");
            }
            log.info("限制请求'{}',当前请求'{}',缓存key'{}'", count, number.intValue(), key);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("{}发生错误，错误为:{}",this.getClass().getName(),e.getMessage());
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }

    }

    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuilder stringBuffer = new StringBuilder(rateLimiter.key() + ":");
        if (rateLimiter.limitType() == LimitType.IP) {
            stringBuffer.append(ServletUtil.getClientIP(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest())).append("-");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }
}
