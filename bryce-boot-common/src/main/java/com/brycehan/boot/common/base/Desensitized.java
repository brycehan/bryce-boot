package com.brycehan.boot.common.base;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 数据脱敏注解
 *
 * @author Bryce Han
 * @since 2024/6/23
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizedSerializer.class)
public @interface Desensitized {

    /**
     * 脱敏类型
     * @return 脱敏类型
     */
    DesensitizedType type();

    /**
     * 前N位不脱敏
     * @return 前N位不脱敏
     */
    int frontLength() default 0;

    /**
     * 后N位不脱敏
     * @return 后N位不脱敏
     */
    int endLength() default 0;

}
