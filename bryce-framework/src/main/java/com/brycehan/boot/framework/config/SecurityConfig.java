package com.brycehan.boot.framework.config;

import com.brycehan.boot.framework.filter.JwtAuthenticationFilter;
import com.brycehan.boot.framework.security.JwtAccessDeniedHandler;
import com.brycehan.boot.framework.security.JwtAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.annotation.PostConstruct;

/**
 * Spring Security 配置
 *
 * @author Bryce Han
 * @since 2022/5/9
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    /**
     * httpSecurity 配置
     * <p>
     * anyRequest       匹配任何请求路径
     * anonymous        匿名可以访问
     * hasAnyAuthority  如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole       如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority     如果有参数，参数表示权限，则其权限可以访问
     * hasRole          如果有参数，参数表示角色，则其角色可以访问
     * hasIpAddress     如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * access           SpringEL表达式结果为true时可以访问
     * rememberMe       允许通过remember-me登录的用户访问
     * authenticated    用户登录后可以访问
     * fullyAuthenticated   用户完全认证可以访问（非remember-me下的自动登录）
     * permitAll        用户可以任意访问
     * denyAll          用户不能访问
     *
     * @param http the {@link HttpSecurity} to modify
     * @return SecurityFilterChain实例
     * @throws Exception 如果发生错误时
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 禁用csrf，jwt不需要csrf开启
                .csrf().disable()
                // 禁用X-Frame-Options
                .headers()
                .frameOptions().disable()
                // 开启跨域
                .and().cors()

                .and().exceptionHandling()
                // 认证失败处理
                .authenticationEntryPoint(authenticationEntryPoint)
                // 无权限访问处理
                .accessDeniedHandler(accessDeniedHandler)

                // 基于token，不需要session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 过滤请求
                .and()
                .authorizeHttpRequests()
                // 对于登录login、注册register、注册开关，验证码captcha允许匿名访问
                .requestMatchers(HttpMethod.POST, "/auth/login", "/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/register/enabled", "/captcha", "/error").permitAll()
                // todo test
//                .requestMatchers("/system/post/**").permitAll()
                .requestMatchers("/system/menu/**").permitAll()
                // 校验令牌允许访问
                .requestMatchers(HttpMethod.POST,
                        "/auth/validateToken").permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                // 静态资源，可以匿名访问
                .requestMatchers(HttpMethod.GET,
                        "/webjars/**",
                        "/swagger-ui/index.html",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/api-docs/**",
                        "/favicon.ico",
                        "/upload/**"
                ).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()

                .and()
                // 添加 jwt 过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加 退出 过滤器
                .logout().logoutUrl("/auth/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and().build();
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
