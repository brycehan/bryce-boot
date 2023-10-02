package com.brycehan.boot.common.base;

/**
 * Redis Key 管理
 *
 * @author Bryce Han
 * @since 2023/8/28
 */
public class RedisKeys {

    public static String getOperateLogKey() {
        return "sys:log";
    }

}
