package com.brycehan.boot.common.util;

/**
 * 安全工具类
 *
 * @since 2023/4/19
 * @author Bryce Han
 */
public class SecurityUtils {

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 判断结果
     */
    public static boolean isAdmin(Long userId){
        // todo test
//        return UserConstants.ADMIN_USER_ID.equals(userId);
        return false;
    }
}
