package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.http.ResponseResult;

import java.util.LinkedHashMap;

/**
 * 短信服务
 *
 * @since 2023/10/8
 * @author Bryce Han
 */
public interface SmsService {

    /**
     * 发送手机短信
     *
     * @param phone 手机号
     * @param templateId 短信模板ID
     * @param params 短信参数
     * @return 发送状态（true：成功，false：失败）
     */
    boolean send(String phone, String templateId, LinkedHashMap<String, String> params);

    /**
     * 发送手机短信
     *
     * @param phone 手机号
     * @param type 短信类型
     * @param templateId 短信模板ID
     * @param params 短信参数
     * @return 发送状态（true：成功，false：失败）
     */
    ResponseResult<?> send(String phone, String type, String templateId, LinkedHashMap<String, String> params);

    /**
     * 是否开启短信功能
     *
     * @return 开启标识（true：开启，false：关闭）
     */
    boolean isSmsEnabled();

}
