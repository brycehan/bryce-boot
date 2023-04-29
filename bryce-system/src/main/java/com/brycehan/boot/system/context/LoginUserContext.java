package com.brycehan.boot.system.context;

import com.brycehan.boot.common.base.http.HttpResponseStatusEnum;
import com.brycehan.boot.common.exception.BusinessException;
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
            log.info("安全上下文中没有认证信息，异常信息：{}", e.getMessage());
            throw BusinessException.responseStatus(HttpResponseStatusEnum.HTTP_UNAUTHORIZED);
        }
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 当前登录用户ID
     */
    public static String currentUserId() {
        return currentUser().getId();
    }

}
