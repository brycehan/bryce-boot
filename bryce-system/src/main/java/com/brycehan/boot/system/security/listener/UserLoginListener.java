package com.brycehan.boot.system.security.listener;

import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.MessageUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.security.event.UserLoginFailedEvent;
import com.brycehan.boot.framework.security.event.UserLoginSuccessEvent;
import com.brycehan.boot.system.service.AuthService;
import com.brycehan.boot.system.service.SysLoginInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

/**
 * 用户登录事件监听器
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
@Component
@RequiredArgsConstructor
public class UserLoginListener {

    private final SysLoginInfoService sysLoginInfoService;

    private final AuthService authService;

    /**
     * 登录成功事件处理
     *
     * @param userLoginSuccessEvent 登录成功事件
     */
    @Async
    @EventListener
    public void onSuccess(UserLoginSuccessEvent userLoginSuccessEvent) {

        String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
        String ip = IpUtils.getIpAddress(ServletUtils.getRequest());
        // 1、异步记录登录日志
        sysLoginInfoService.AsyncRecordLoginInfo(userAgent, ip, userLoginSuccessEvent.getLoginUser().getUsername(),
                CommonConstants.LOGIN_SUCCESS,
                MessageUtils.getMessage("user.login.success"));
        // 2、更新用户登录信息
        this.authService.updateLoginInfo(userLoginSuccessEvent.getLoginUser());
    }

    /**
     * 登录失败事件处理
     *
     * @param userLoginFailedEvent 登录失败事件
     */
    @Async
    @EventListener
    public void onFailed(UserLoginFailedEvent userLoginFailedEvent) {

        String message = userLoginFailedEvent.getException().getMessage();
        if (userLoginFailedEvent.getException() instanceof BadCredentialsException) {
            message = MessageUtils.getMessage("user.username.or.password.error");
        }
        String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
        String ip = IpUtils.getIpAddress(ServletUtils.getRequest());
        // 异步记录登录日志
        sysLoginInfoService.AsyncRecordLoginInfo(userAgent, ip, userLoginFailedEvent.getLoginUser().getUsername(),
                CommonConstants.LOGIN_FAIL,
                message);
    }
}
