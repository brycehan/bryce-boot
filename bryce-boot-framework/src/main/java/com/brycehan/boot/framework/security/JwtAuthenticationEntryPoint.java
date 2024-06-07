package com.brycehan.boot.framework.security;

import com.brycehan.boot.common.base.http.HttpResponseStatus;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.util.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 认证不通过后的处理
 *
 * @since 2022/5/7
 * @author Bryce Han
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        log.error("验证不通过，请求地址：{}，提示信息：{}", request.getRequestURI(), authException.getMessage());
        ServletUtils.render(response, ResponseResult.error(HttpResponseStatus.HTTP_UNAUTHORIZED));
    }

}
