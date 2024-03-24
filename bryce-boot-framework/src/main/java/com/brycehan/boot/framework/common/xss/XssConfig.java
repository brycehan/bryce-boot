package com.brycehan.boot.framework.common.xss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.PathMatcher;

/**
 * Xss 配置文件
 *
 * @since 2023/10/26
 * @author Bryce Han
 */
@Configuration
@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnProperty(name = "bryce.xss.enabled", havingValue = "true")
public class XssConfig {

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter(XssProperties xssProperties, PathMatcher pathMatcher) {
        FilterRegistrationBean<XssFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new XssFilter(xssProperties, pathMatcher));
        bean.setOrder(Integer.MAX_VALUE);
        bean.setName("xssFilter");

        return bean;
    }
}
