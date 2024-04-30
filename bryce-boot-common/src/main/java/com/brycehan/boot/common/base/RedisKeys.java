package com.brycehan.boot.common.base;

import com.brycehan.boot.common.constant.CacheConstants;

/**
 * Redis Key 管理
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
public class RedisKeys {

    /**
     * 获取验证码 key
     *
     * @param key 验证码 uuid
     * @return 验证码 key
     */
    public static String getCaptchaKey(String key) {
        return CacheConstants.CAPTCHA_CODE_KEY.concat(key);
    }

    /**
     * 获取操作日志 key
     *
     * @return 操作日志 key
     */
    public static String getOperateLogKey() {
        return "sys:log";
    }

    /**
     * 获取第三方登录 key
     *
     * @return 第三方登录 key
     */
    public static String getThirdLoginKey() {
        return "third:login";
    }

}
