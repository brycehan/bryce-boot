package com.brycehan.boot.api.sms;

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
     * @param templateId 模板ID
     * @param params 参数
     * @return 是否发送成功
     */
    Boolean send(@RequestParam String phone, @RequestParam String templateId, @RequestParam LinkedHashMap<String, String> params);

    /**
     * 校验短信验证码
     *
     * @param phone 手机号
     * @param templateId 模板ID
     * @param code 验证码
     * @return 是否校验成功
     */
    Boolean verifyCode(String phone, String templateId, String code);

    /**
     * 是否开启短信功能
     *
     * @return 开启标识（true：开启，false：关闭）
     */
    boolean isSmsEnabled();

}
