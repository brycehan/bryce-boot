package com.brycehan.boot.common.base.context;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

/**
 * Spring上下文工具类
 *
 * @since 2022/5/11
 * @author Bryce Han
 */
@Configuration
@AutoConfigureOrder(Integer.MIN_VALUE)
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 获取bean
     *
     * @param name bean名称
     * @param <T>  bean的类型
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        if (!ObjectUtils.isEmpty(SpringContextHolder.applicationContext)) {
            return (T) SpringContextHolder.applicationContext.getBean(name);
        }
        return null;
    }

    /**
     * 获取bean
     *
     * @param requiredType bean的class类型
     * @param <T>          bean class类型
     * @return bean实例
     */
    public static <T> T getBean(Class<T> requiredType) {
        if (!ObjectUtils.isEmpty(SpringContextHolder.applicationContext)) {
            return SpringContextHolder.applicationContext.getBean(requiredType);
        }
        return null;
    }

    /**
     * 设置Spring上下文环境
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException Beans异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

}
