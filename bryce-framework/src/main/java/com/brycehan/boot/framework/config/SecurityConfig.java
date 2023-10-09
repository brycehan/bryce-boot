package com.brycehan.boot.framework.config;

import com.brycehan.boot.framework.filter.JwtAuthenticationFilter;
import com.brycehan.boot.framework.security.JwtAccessDeniedHandler;
import com.brycehan.boot.framework.security.JwtAuthenticationEntryPoint;
import com.brycehan.boot.framework.security.phone.PhoneCodeAuthenticationProvider;
import com.brycehan.boot.framework.security.phone.PhoneCodeUserDetailsService;
import com.brycehan.boot.framework.security.phone.PhoneCodeValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security 配置
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final UserDetailsService userDetailsService;

    private final UserDetailsChecker userDetailsChecker;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final PhoneCodeUserDetailsService phoneCodeUserDetailsService;

    private final PhoneCodeValidateService phoneCodeValidateService;

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider());
        providers.add(phoneCodeAuthenticationProvider());

        ProviderManager providerManager = new ProviderManager(providers);
        providerManager.setAuthenticationEventPublisher(new DefaultAuthenticationEventPublisher(applicationEventPublisher));

        return providerManager;
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPreAuthenticationChecks(userDetailsChecker);
        return daoAuthenticationProvider;
    }

    @Bean
    PhoneCodeAuthenticationProvider phoneCodeAuthenticationProvider() {
        return new PhoneCodeAuthenticationProvider(phoneCodeUserDetailsService, phoneCodeValidateService);
    }

    /**
     * httpSecurity 配置
     * <p>
     * hasIpAddress     如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * access           SpringEL表达式结果为true时可以访问
     * rememberMe       允许通过remember-me登录的用户访问
     * authenticated    用户登录后可以访问
     * fullyAuthenticated   用户完全认证可以访问（非remember-me下的自动登录）
     *
     * @param http the {@link HttpSecurity} to modify
     * @return SecurityFilterChain实例
     * @throws Exception 如果发生错误时
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 禁用csrf，jwt不需要csrf开启
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用X-Frame-Options
                .headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // 基于token，不需要session
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 过滤请求
                .authorizeHttpRequests(registry -> registry
                        // 静态资源，可以匿名访问
                        .requestMatchers(HttpMethod.GET,
                                "/webjars/**",
                                "/swagger-ui/index.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/favicon.ico",
                                "/upload/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/register/enabled",
                                "/captcha/enabled",
                                "/captcha/generate",
                                "/sms/**",
                                "/error").permitAll()
                        // 对于登录login、注册register、注册开关，验证码captcha允许匿名访问
                        .requestMatchers(HttpMethod.POST,
                                "/auth/loginByAccount",
                                "/auth/loginByPhone",
                                "/register",
                                "/auth/validateToken").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated())
//                .httpBasic(withDefaults())
                // 添加 jwt 过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .build();
    }

    /**
     * 使用BCrypt强哈希函数密码加密实现
     *
     * @return 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
