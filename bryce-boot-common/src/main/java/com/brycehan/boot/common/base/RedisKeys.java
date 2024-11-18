package com.brycehan.boot.common.base;

import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.enums.SmsType;

import java.time.LocalDate;

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
     * 获取短信验证码key
     *
     * @param phone 手机号码
     * @return 验证码key
     */
    public static String getSmsCodeKey(String phone, SmsType smsType) {
        return CacheConstants.SMS_CODE_KEY + smsType.getValue() + ":" + phone;
    }

    /**
     * 获取当天短信发送次数key
     *
     * @param phone 手机号码
     * @return 短信发送次数key
     */
    public static String getSmsTodayCountKey(String phone) {
        LocalDate today = LocalDate.now();
        return CacheConstants.SMS_COUNT_KEY + today + ":" + phone;
    }

    /**
     * 获取第三方登录 key
     *
     * @return 第三方登录 key
     */
    public static String getThirdLoginKey(String state) {
        return CacheConstants.SYSTEM_THIRDLOGIN_KEY + state;
    }

}
