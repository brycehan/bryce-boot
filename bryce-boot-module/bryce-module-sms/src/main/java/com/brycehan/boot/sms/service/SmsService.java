package com.brycehan.boot.sms.service;

import java.util.LinkedHashMap;

/**
 * 短信服务
 *
 * @author Bryce Han
 * @since 2023/10/8
 */
public interface SmsService {

    /**
     * 发送手机短信
     *
     * @param phone      手机号
     * @param templateId 短信模板ID
     * @param params     短信参数
     * @return 发送状态（true：成功，false：失败）
     */
    boolean send(String phone, String templateId, LinkedHashMap<String, String> params);

}
