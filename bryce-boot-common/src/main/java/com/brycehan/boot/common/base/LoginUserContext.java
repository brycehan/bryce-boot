package com.brycehan.boot.common.base;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录用户上下文
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Slf4j
public class LoginUserContext {

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户
     */
    public static LoginUser currentUser() {
        return LoginUserContextHolder.getContext();
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

    /**
     * 获取当前登录用户的机构ID
     *
     * @return 当前登录用户的机构ID
     */
    @SuppressWarnings("unused")
    public static Long currentOrgId() {
        LoginUser loginUser = currentUser();
        if(loginUser == null){
            return null;
        }

        return loginUser.getOrgId();
    }

}
