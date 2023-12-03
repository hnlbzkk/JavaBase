package com.xyz.base.annotation.aspect;

import com.xyz.base.utils.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogApiAspect {

    /**
     * todo: modify package name
     */
    @Pointcut("@annotation(com.xyz.base.annotation.LogApi)")
    public void logApi() {}

    /**
     * 环绕通知使用Around
     */
    @Around("logApi()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 通过反射获取被调用方法的Class
        Class type = joinPoint.getSignature().getDeclaringType();
        // 获取日志记录对象Logger
        Logger logger = LoggerFactory.getLogger(type);

        // 执行结果
        Object res;
        try {
            // 执行目标方法，获取执行结果
            res = joinPoint.proceed();
            logger.info(LogUtils.success());
        } catch (Exception e) {
            System.out.println(LogUtils.error(e.getMessage()));
            // 如果发生异常，则抛出异常
            throw e;
        }
        // 返回执行结果
        return res;
    }
}
