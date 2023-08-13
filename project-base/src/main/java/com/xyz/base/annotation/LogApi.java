package com.xyz.base.annotation;

import java.lang.annotation.*;

/**
 * 在方法上添加注解,日志记录
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LogApi {
    String value() default "";
}
