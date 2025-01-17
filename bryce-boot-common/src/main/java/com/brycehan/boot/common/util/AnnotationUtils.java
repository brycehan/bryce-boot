package com.brycehan.boot.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * 注解工具类
 *
 * @author Bryce Han
 * @since 2025/1/17
 */
public class AnnotationUtils {

    /**
     * 获取类中指定注解的属性
     *
     * @param clazz          类
     * @param annotationClazz 注解类
     * @param <T>            泛型
     * @return 属性
     */
    public static <T> Optional<Field> getAnnotationField(Class<T> clazz, Class<? extends Annotation> annotationClazz) {
        if (clazz == null || annotationClazz == null) {
            return Optional.empty();
        }

        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(annotationClazz)).findFirst();
    }
}
