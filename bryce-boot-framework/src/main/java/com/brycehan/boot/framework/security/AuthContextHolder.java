package com.brycehan.boot.framework.security;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 认证权限持有者
 *
 * @author Bryce Han
 * @since 2024/12/13
 */
public class AuthContextHolder {
    private static final String AUTH_CONTEXT_ATTRIBUTE = "AUTH_CONTEXT";

    /**
     * 设置权限上下文
     *
     * @param authority 权限
     */
    public static void setContext(String... authority) {
        RequestContextHolder.currentRequestAttributes().setAttribute(AUTH_CONTEXT_ATTRIBUTE, authority, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 获取认证上下文
     *
     * @return 认证上下文
     */
    public static String[] getContext() {
        return (String[]) RequestContextHolder.currentRequestAttributes().getAttribute(AUTH_CONTEXT_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
    }

}
