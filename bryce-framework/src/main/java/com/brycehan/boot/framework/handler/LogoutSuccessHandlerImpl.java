package com.brycehan.boot.framework.handler;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.util.HttpContextUtils;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.common.util.MessageUtils;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.system.context.LoginUser;
import com.brycehan.boot.system.service.SysLoginInfoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义退出处理器
 *
 * @author Bryce Han
 * @since 2022/10/9
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    private final SysLoginInfoService sysLoginInfoService;

    public LogoutSuccessHandlerImpl(JwtTokenProvider jwtTokenProvider, SysLoginInfoService sysLoginInfoService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.sysLoginInfoService = sysLoginInfoService;
    }

    /**
     * 退出处理逻辑
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 认证
     * @throws IOException IO异常
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        LoginUser loginUser = this.jwtTokenProvider.getLoginUser(request);
        if (Objects.nonNull(loginUser)) {
            // 1、删除登录用户缓存记录
            this.jwtTokenProvider.deleteLoginUser(loginUser.getToken());
            // 子线程共享请求request数据
            RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
            // 2、记录用户退出日志
            String userAgent = HttpContextUtils.getRequest().getHeader("User-Agent");
            sysLoginInfoService.AsyncRecordLoginInfo(userAgent, loginUser.getUsername(), CommonConstants.LOGOUT_SUCCESS, MessageUtils.message("user.logout.success"));
        }

        // 3、返回响应结果
        HttpContextUtils.renderString(response, JsonUtils.objectMapper.writeValueAsString(ResponseResult.ok(null, MessageUtils.message("user.logout.success"))));
    }

}
