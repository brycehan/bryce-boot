package com.brycehan.boot.common.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典翻译注解
 *
 * @author Bryce Han
 * @since 2024/11/3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Trans {

    /**
     * 字典类型
     * @return 字典类型
     */
    String dict();

    /**
     * 本类的哪个字段需要反向翻译
     *
     * @return 字典类型引用字段
     */
    String ref() default "";

}
