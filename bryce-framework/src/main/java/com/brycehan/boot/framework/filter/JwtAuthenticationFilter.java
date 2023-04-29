package com.brycehan.boot.framework.filter;

import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.system.context.LoginUser;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * Jwt认证过滤器
 *
 * @author Bryce Han
 * @since 2022/5/13
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 请求资源地址
        String requestURI = request.getRequestURI();
        // 1、根据请求头获取登录用户
        LoginUser loginUser = this.jwtTokenProvider.getLoginUser(request);


        if (Objects.nonNull(loginUser) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            // 2、令牌续期
            this.jwtTokenProvider.autoRefreshToken(loginUser);
            // 3、设置认证信息
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            log.debug("set Authentication to security context for '{}', uri: {}", loginUser.getUsername(), requestURI);
        } else {
            log.debug("not valid Jwt token found, uri: {}", requestURI);
        }

        filterChain.doFilter(request, response);
    }
}
