package com.brycehan.boot.framework.security;

import com.brycehan.boot.common.base.LoginUserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 安全上下文拦截器
 *
 * @author Bryce Han
 * @since 2024/5/26
 */
@Slf4j
@Component
public class SecurityContextInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        LoginUserContextHolder.clearContext();
    }
}
