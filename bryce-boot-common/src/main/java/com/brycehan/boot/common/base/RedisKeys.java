package com.brycehan.boot.common.base;

/**
 * Redis Key 管理
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
public class RedisKeys {

    public static String getOperateLogKey() {
        return "sys:log";
    }

}
