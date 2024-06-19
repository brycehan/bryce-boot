package com.brycehan.boot.common.base;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 登录用户 Holder
 *
 * @author Bryce Han
 * @since 2024/6/20
 */
public class LoginUserContextHolder {

    private static final InheritableThreadLocal<LoginUser> CONTEXT_HOLDER = new TransmittableThreadLocal<>();

    /**
     * 设置登录用户
     *
     * @param loginUser 登录用户
     */
    public static void setContext(LoginUser loginUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        // 新建 SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        CONTEXT_HOLDER.set(loginUser);
    }

    /**
     * 获取登录用户
     *
     * @return 登录用户
     */
    public static LoginUser getContext() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除登录用户
     */
    public static void clearContext() {
        SecurityContextHolder.clearContext();
        CONTEXT_HOLDER.remove();
    }

}
