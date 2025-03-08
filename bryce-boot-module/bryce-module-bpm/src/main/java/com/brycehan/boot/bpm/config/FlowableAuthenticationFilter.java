package com.brycehan.boot.bpm.config;

import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.identity.Authentication;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Bpm 认证过滤器
 *
 * @since 2025/2/22
 * @author Bryce Han
 */
@Slf4j
@Component
@SuppressWarnings("NullableProblems")
@RequiredArgsConstructor
public class FlowableAuthenticationFilter extends OncePerRequestFilter implements Ordered  {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // 获取登录用户
            LoginUser loginUser = LoginUserContext.currentUser();

            if(loginUser != null) {
                // 设置Flowable工作流的当前登录的用户
                Authentication.setAuthenticatedUserId(String.valueOf(loginUser.getId()));
                log.debug("将认证信息设置到 Flowable 中，username：{}", loginUser.getUsername());
            }

            filterChain.doFilter(request, response);
        } finally {
            Authentication.setAuthenticatedUserId(null);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
