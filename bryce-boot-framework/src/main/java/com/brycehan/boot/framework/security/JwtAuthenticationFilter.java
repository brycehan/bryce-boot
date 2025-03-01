package com.brycehan.boot.framework.security;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.response.HttpResponseStatus;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.common.util.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Jwt认证过滤器
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter implements Ordered {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        log.info("请求URI，{}", request.getRequestURI());
        String accessToken = TokenUtils.getAccessToken(request);
        // accessToken 为空，表示未登录
        if(StrUtil.isBlank(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        LoginUser loginUser = null;

        // 校验并解析令牌
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtTokenProvider.validateToken(accessToken);
        } catch (TokenExpiredException e) {
            ServletUtils.render(response, ResponseResult.error(HttpResponseStatus.HTTP_UNAUTHORIZED.code(), "登录状态已过期"));
            return;
        } catch (Exception e) {
            ServletUtils.render(response, ResponseResult.error(HttpResponseStatus.HTTP_UNAUTHORIZED.code(), "访问令牌校验失败"));
            return;
        }

        Map<String, Claim> claimMap = decodedJWT.getClaims();
        // 设置用户信息到请求头
        String userKey = JwtTokenProvider.getUserKey(claimMap);
        String userData = JwtTokenProvider.getUserData(claimMap);

        // 获取登录用户
        if(StringUtils.isNotEmpty(userKey)) {
            loginUser = jwtTokenProvider.loadLoginUser(userKey);
        } else if (StringUtils.isNotEmpty(userData)) {
            loginUser = JsonUtils.readValue(URLDecoder.decode(userData, StandardCharsets.UTF_8), LoginUser.class);
        }

        if(loginUser == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 用户存在，自动刷新令牌
        jwtTokenProvider.autoRefreshToken(loginUser);

        // 设置认证信息
        LoginUserContext.setContext(loginUser);
        log.info("将认证信息设置到安全上下文中，username：{}", loginUser.getUsername());

        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
