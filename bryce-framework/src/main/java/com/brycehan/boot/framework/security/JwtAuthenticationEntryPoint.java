package com.brycehan.boot.framework.security;

import com.brycehan.boot.common.base.http.HttpResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证不通过后的处理
 *
 * @author Bryce Han
 * @since 2022/5/7
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("验证不通过，请求地址：{}，提示信息：{}", request.getRequestURI(), authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, HttpResponseStatusEnum.HTTP_UNAUTHORIZED.message());
    }

}
