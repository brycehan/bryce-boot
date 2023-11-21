package com.brycehan.boot.system.security.listener;

import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.system.common.LoginInfoType;
import com.brycehan.boot.system.service.AuthService;
import com.brycehan.boot.system.service.SysLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 认证事件监听器
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
@Component
@RequiredArgsConstructor
public class UserLoginListener {

    private final SysLoginLogService sysLoginLogService;

    private final AuthService authService;

    /**
     * 登录成功事件处理
     *
     * @param event 认证成功事件
     */
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        // 用户信息
        UserDetails user = (UserDetails) event.getAuthentication().getPrincipal();
        // 记录登录日志
        this.sysLoginLogService.save(user.getUsername(), DataConstants.SUCCESS, LoginInfoType.LOGIN_SUCCESS.getValue());
        // 更新用户登录信息
        this.authService.updateLoginInfo(user);
    }

    /**
     * 登录失败事件处理
     *
     * @param authenticationFailureEvent 认证失败事件
     */
    @EventListener
    public void onFailed(AbstractAuthenticationFailureEvent authenticationFailureEvent) {
        // 用户名
        String username = (String) authenticationFailureEvent.getAuthentication().getPrincipal();
        // 记录登录日志
        this.sysLoginLogService.save(username, CommonConstants.LOGIN_FAIL, LoginInfoType.LOGIN_SUCCESS.getValue());
    }
}
