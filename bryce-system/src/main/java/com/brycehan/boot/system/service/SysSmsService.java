package com.brycehan.boot.system.service;

/**
 * 短信服务
 *
 * @author Bryce Han
 * @since 2022/12/6
 */
public interface SysSmsService {

    /**
     * 发送手机短信
     *
     * @param phoneNumber 手机号码
     * @param message     短信内容
     */
    void sendSms(String phone, String message);

}
