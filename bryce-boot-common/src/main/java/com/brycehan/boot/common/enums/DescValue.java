package com.brycehan.boot.common.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述信息
 *
 * @author Bryce Han
 * @since 2025/1/17
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DescValue {

    /**
     * 描述信息内容
     */
    String value() default "";
}
