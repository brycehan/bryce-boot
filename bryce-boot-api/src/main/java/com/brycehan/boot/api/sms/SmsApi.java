package com.brycehan.boot.api.sms;

import com.brycehan.boot.common.enums.SmsType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;

/**
 * 短信服务
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface SmsApi {

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @param smsType 短信类型
     * @param params 参数
     * @return 是否发送成功
     */
    Boolean send(@RequestParam String phone, @RequestParam SmsType smsType, @RequestParam LinkedHashMap<String, String> params);

}
