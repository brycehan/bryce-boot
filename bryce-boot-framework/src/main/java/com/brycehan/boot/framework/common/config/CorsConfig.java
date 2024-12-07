package com.brycehan.boot.framework.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域资源共享配置
 *
 * @since 2022/5/6
 * @author Bryce Han
 */
@Configuration
public class CorsConfig {

    /**
     * 跨域过滤器
     *
     * @return CorsFilter
     */
    public CorsFilter corsFilter(){
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        // 设置访问源地址
        corsConfiguration.addAllowedOriginPattern("*");
        // 设置访问源请求头
        corsConfiguration.addAllowedHeader("*");
        // 设置访问源请求方法
        corsConfiguration.addAllowedMethod("*");
        // 预检请求的缓存时间7200秒
        corsConfiguration.setMaxAge(7200L);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 添加映射路径，拦截一切请求
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    /**
     * 注册跨域过滤器并指定顺序
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(corsFilter());
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        return registration;
    }

}
