package com.xyz.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口防刷
 *
 * @author ZKKzs
 * @since 2023/8/14
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AccessLimit {

    /**
     * @return 多少秒内
     */
    int second() default 10;

    /**
     * @return 最大访问次数
     */
    int maxRequestCount() default 8;

    /**
     * @return 禁用时长 /s
     */
    int forbiddenTime() default 120;
}
