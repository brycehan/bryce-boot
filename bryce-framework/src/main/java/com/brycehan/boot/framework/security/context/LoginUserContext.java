package com.brycehan.boot.framework.security.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 登录用户上下文
 *
 * @author Bryce Han
 * @since 2021/8/31
 */
@Slf4j
public class LoginUserContext {

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户
     */
    public static LoginUser currentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (LoginUser) authentication.getPrincipal();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 当前登录用户ID
     */
    public static Long currentUserId() {
        LoginUser loginUser = currentUser();
        if(loginUser == null){
            return null;
        }

        return loginUser.getId();
    }

}
