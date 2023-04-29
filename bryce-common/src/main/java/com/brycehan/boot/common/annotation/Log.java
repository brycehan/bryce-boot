package com.brycehan.boot.common.annotation;

import com.brycehan.boot.common.enums.BusinessType;
import com.brycehan.boot.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 操作日志记录注解
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 功能标题
     *
     * @return 功能标题
     */
    String title() default "";

    /**
     * 业务类型
     *
     * @return 业务类型
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     *
     * @return 操作人类别
     */
    OperatorType operatorType() default OperatorType.MANAGER;

    /**
     * 是否保存请求的数据
     *
     * @return 是/否
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的数据
     *
     * @return 是/否
     */
    boolean isSaveResponseData() default true;

}
