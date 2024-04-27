package com.brycehan.boot.framework.security;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.interfaces.Claim;
import com.brycehan.boot.api.ma.MaUserApi;
import com.brycehan.boot.api.ma.vo.MaUserApiVo;
import com.brycehan.boot.api.system.SysUserApi;
import com.brycehan.boot.common.base.context.LoginUser;
import com.brycehan.boot.common.constant.JwtConstants;
import com.brycehan.boot.framework.common.SourceClientType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MaUserApi maUserApi;
    private final SysUserApi sysUserApi;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        String accessToken = TokenUtils.getAccessToken(request);
        // accessToken 为空，表示未登录
        if(StrUtil.isBlank(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 来源客户端
        String sourceClient = TokenUtils.getSourceClient(request);
        SourceClientType sourceClientType = SourceClientType.getByValue(sourceClient);

        LoginUser loginUser;

        if(SourceClientType.MINI_APP.value().equals(sourceClient)) {
            // 获取小程序登录用户
            loginUser = this.processMiniApp(accessToken);
        } else if (SourceClientType.APP.value().equals(sourceClient)){
            loginUser = this.processApp(accessToken);
        } else {
            // 获取其它登录用户
            loginUser = this.jwtTokenProvider.loadLoginUser(accessToken, sourceClientType);
        }

        if(loginUser == null) {
            filterChain.doFilter(request, response);
            return;
        }

        loginUser.setSourceClient(sourceClient);

        // 用户存在，自动刷新令牌
        this.jwtTokenProvider.autoRefreshToken(loginUser);

        // 设置认证信息
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        // 新建 SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        log.debug("将认证信息设置到安全上下文中，'{}', uri: {}", loginUser.getUsername(), request.getRequestURI());

        filterChain.doFilter(request, response);
    }

    /**
     * 处理小程序登录
     *
     * @param accessToken 访问令牌
     * @return 登录用户
     */
    private LoginUser processMiniApp(String accessToken) {
        // 校验 token
        boolean validated = this.jwtTokenProvider.validateToken(accessToken);
        if(!validated) {
            throw new RuntimeException("token无效");
        }
        
        // 获取登录用户
        // 解析对应的权限以及用户信息
        Map<String, Claim> claimMap = this.jwtTokenProvider.parseToken(accessToken);
        this.jwtTokenProvider.autoRefreshToken(claimMap);

        String openid = claimMap.get(JwtConstants.LOGIN_OPEN_ID).asString();
        MaUserApiVo maUserApiVo = this.maUserApi.loadMaUserByOpenid(openid);

        // 设置认证信息
        return SecurityUtils.createLoginUser(maUserApiVo);
    }

    /**
     * 处理App程序登录
     *
     * @param accessToken 访问令牌
     * @return 登录用户
     */
    private LoginUser processApp(String accessToken) {
        // 校验 token
        boolean validated = this.jwtTokenProvider.validateToken(accessToken);
        if(!validated) {
            throw new RuntimeException("token无效");
        }

        // 获取登录用户
        // 解析对应的权限以及用户信息
        Map<String, Claim> claimMap = this.jwtTokenProvider.parseToken(accessToken);
        this.jwtTokenProvider.autoRefreshToken(claimMap);

        Long id = claimMap.get(JwtConstants.LOGIN_USER_APP_KEY).asLong();

        return this.sysUserApi.loadUserById(id);
    }
}
