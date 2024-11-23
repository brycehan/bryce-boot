package com.brycehan.boot.common.util;

import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 校验工具类
 *
 * @author Bryce Han
 * @since 2024/11/23
 */
@Slf4j
public class ValidatorUtils {

    /**
     * 校验对象
     *
     * @param validator   验证器
     * @param object      待校验对象
     * @param groups      待校验的组
     * @throws ConstraintViolationException 校验不通过
     */
    public static void validate(Validator validator, Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ExcelConstraintViolationException(constraintViolations);
        }
    }

    /**
     * Excel校验不通过异常
     */
    public static class ExcelConstraintViolationException extends ConstraintViolationException{

        public ExcelConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
            super(toString(constraintViolations), constraintViolations);
        }

        private static String toString(Set<? extends ConstraintViolation<?>> constraintViolations) {
            List<String> messages = new ArrayList<>();

            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                // 获取校验失败的字段名称
                Path propertyPath = constraintViolation.getPropertyPath();
                PathImpl pathImpl =(PathImpl) propertyPath;
                String name = pathImpl.getLeafNode().getName();
                try {
                    // 获取校验失败的字段Field
                    Field field = constraintViolation.getRootBeanClass().getDeclaredField(name);
                    // 获取字段上的ExcelProperty注解
                    ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
                    String[] value = annotation.value();
                    // 组装校验失败的信息
                    messages.add(value[0] + constraintViolation.getMessage());
                } catch (NoSuchFieldException e) {
                    log.error("获取ExcelProperty注解失败", e);
                    messages.add(constraintViolation.getMessage());
                }
            }
            return String.join(", ", messages);
        }
    }

}
