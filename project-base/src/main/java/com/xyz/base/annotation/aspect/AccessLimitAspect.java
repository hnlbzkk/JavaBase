package com.xyz.base.annotation.aspect;

import com.xyz.base.annotation.AccessLimit;
import com.xyz.base.exception.ServiceException;
import com.xyz.base.message.ResultCode;
import com.xyz.db.redis.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.xyz.base.constant.Constant.COUNT_PREFIX;
import static com.xyz.base.constant.Constant.LOCK_PREFIX;


/**
 * @author ZKKzs
 * @since 2023/8/14
 **/
@Aspect
@Component
public class AccessLimitAspect {

    private final Logger logger = LoggerFactory.getLogger(AccessLimitAspect.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * todo: modify package name
     */
    @Pointcut("execution(public * com.xyz.*.*Controller.*(..)) || @annotation(com.xyz.base.annotation.AccessLimit)")
    public void accessLimit() {
    }

    @Before("accessLimit()")
    public void around(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        AccessLimit annotation = method.getAnnotation(AccessLimit.class);
        int second = annotation.second();
        int maxRequestCount = annotation.maxRequestCount();
        int forbiddenTime = annotation.forbiddenTime();

        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        if (isForbidden(second, maxRequestCount, forbiddenTime, ip, uri)) {
            throw new ServiceException(ResultCode.IP_FORBIDDEN);
        }
    }

    private boolean isForbidden(int second, int maxRequestCount, int forbiddenTime, String ip, String uri) {
        String lockKey = LOCK_PREFIX + ip + uri;
        Integer lockCount = redisUtil.getCacheObject(lockKey);

        if (Objects.isNull(lockCount)) {
            String visitKey = COUNT_PREFIX + ip + uri;
            Integer visitCount = redisUtil.getCacheObject(visitKey);
            if (Objects.isNull(visitCount)) {
                // 首次访问
                logger.info("首次访问 IP = {}", ip);
                redisUtil.setCacheObject(visitKey, 1, second, TimeUnit.SECONDS);
            } else {
                // 之前访问过,但没超过频率
                if (visitCount < maxRequestCount) {
                    redisUtil.setCacheObject(visitKey, visitCount + 1, forbiddenTime, TimeUnit.SECONDS);
                } else {
                    logger.info("IP: {} 禁止访问 {}", ip, uri);
                    redisUtil.setCacheObject(lockKey, 1, forbiddenTime, TimeUnit.SECONDS);
                    redisUtil.deleteObject(visitKey);
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
