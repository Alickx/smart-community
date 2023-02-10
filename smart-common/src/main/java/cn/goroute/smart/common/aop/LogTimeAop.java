package cn.goroute.smart.common.aop;

import cn.goroute.smart.common.annoation.LogTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Method;

/**
 * @author: Alickx
 * @Date: 2023/02/09 21:25:34
 * @Description:
 */
@Configuration
@Aspect
@EnableAspectJAutoProxy
@Slf4j
public class LogTimeAop {

    @Pointcut("@annotation(cn.goroute.smart.common.annoation.LogTime)")
    public void logTime() {
    }

    @Around("logTime()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        LogTime logTime = method.getAnnotation(LogTime.class);
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        Object result;
        long start = System.currentTimeMillis();
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            log.error("Error occurred while executing method {}", methodName, e);
            throw e;
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        log.info("Method {} took {} ms to execute", methodName, time);
        return result;
    }

}
