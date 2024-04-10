package com.brycehan.boot.framework.security;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.brycehan.boot.api.ma.MaUserApi;
import com.brycehan.boot.api.ma.vo.MaUserApiVo;
import com.brycehan.boot.common.constant.JwtConstants;
import com.brycehan.boot.framework.common.SourceClientType;
import com.brycehan.boot.framework.security.context.LoginUser;
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

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        String accessToken = TokenUtils.getAccessToken(request);
        // accessToken 为空，表示未登录
        if(StrUtil.isBlank(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        String sourceClient = TokenUtils.getSourceClient(request);
        LoginUser loginUser = null;
        if(SourceClientType.MINI_APP.value().equals(sourceClient)) {
            // 获取小程序登录用户
            loginUser = this.processMiniApp(accessToken);
        } else if(SourceClientType.PC.value().equals(sourceClient)) {
            // 获取PC登录用户
            loginUser = this.jwtTokenProvider.getLoginUser(accessToken);
        }

        if(loginUser == null) {
            filterChain.doFilter(request, response);
            return;
        }

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
        String openid = claimMap.get(JwtConstants.LOGIN_OPEN_ID).asString();
        
        MaUserApiVo maUserApiVo = this.maUserApi.loadMaUserByOpenid(openid);

        // 设置认证信息
        return SecurityUtils.createLoginUser(maUserApiVo);
    }
}
