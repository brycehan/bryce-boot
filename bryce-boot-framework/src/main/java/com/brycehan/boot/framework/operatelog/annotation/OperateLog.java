package com.brycehan.boot.framework.operatelog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {

    /**
     * 模块名
     */
    String moduleName() default "";

    /**
     * 操作名
     */
    String name() default "";

    /**
     * 操作类型
     */
    OperatedType type() default OperatedType.OTHER;

}
