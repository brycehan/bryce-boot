package com.brycehan.boot.common.util;

import org.springframework.aop.framework.AopContext;

/**
 * Spring工具类
 *
 * @author Bryce Han
 * @since 2024/6/20
 */
public class SpringUtils {

    /**
     * 获取当前AOP代理对象
     *
     * @param target 目标对象
     * @return AOP代理对象
     * @param <T> 目标对象类型
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T> T getAopProxy(T target) {
        return (T) AopContext.currentProxy();
    }

}
