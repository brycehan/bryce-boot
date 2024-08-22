package com.brycehan.boot.framework.common;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

/**
 * 启用 Bryce 配置<br>
 *
 * @author Bryce Han
 * @since 2024/6/20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableRetry
@EnableAsync
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
public @interface EnableBryceConfig {

}
