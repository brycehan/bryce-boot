package com.brycehan.boot.framework.security.config;

import com.brycehan.boot.framework.security.JwtAuthenticationFilter;
import com.brycehan.boot.framework.security.JwtAccessDeniedHandler;
import com.brycehan.boot.framework.security.JwtAuthenticationEntryPoint;
import com.brycehan.boot.framework.security.phone.PhoneCodeAuthenticationProvider;
import com.brycehan.boot.framework.security.phone.PhoneCodeUserDetailsService;
import com.brycehan.boot.framework.security.phone.PhoneCodeValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(AuthProperties.class)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final UserDetailsService userDetailsService;

    private final UserDetailsChecker userDetailsChecker;

    private final PhoneCodeUserDetailsService phoneCodeUserDetailsService;

    private final PhoneCodeValidateService phoneCodeValidateService;

    private final AuthProperties authProperties;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

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
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
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
     * hasIpAddress     如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问<br>
     * access           SpringEL表达式结果为true时可以访问<br>
     * rememberMe       允许通过remember-me登录的用户访问<br>
     * authenticated    用户登录后可以访问<br>
     * fullyAuthenticated   用户完全认证可以访问（非remember-me下的自动登录）
     * </p>
     *
     * @param http the {@link HttpSecurity} to modify
     * @return SecurityFilterChain实例
     * @throws Exception 如果发生错误时
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用退出自动配置接口
                .logout(AbstractHttpConfigurer::disable)
                // 添加 jwt 过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 基于token，不需要session
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 过滤请求
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(this.authProperties.getIgnoreUrls().getAll()).permitAll()
                        .requestMatchers(HttpMethod.GET, this.authProperties.getIgnoreUrls().getGet()).permitAll()
                        .requestMatchers(HttpMethod.POST, this.authProperties.getIgnoreUrls().getPost()).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated())
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                // 禁用X-Frame-Options
                .headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // 禁用csrf，jwt不需要csrf开启
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
