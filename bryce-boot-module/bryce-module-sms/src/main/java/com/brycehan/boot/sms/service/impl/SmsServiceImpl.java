package com.brycehan.boot.sms.service.impl;

import com.brycehan.boot.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 短信服务实现
 *
 * @author Bryce Han
 * @since 2023/10/8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Override
    public boolean send(String phone, String templateId, LinkedHashMap<String, String> params) {

        SmsBlend smsBlend = SmsFactory.getSmsBlend("sms1");

        if (smsBlend == null) {
            throw new RuntimeException("短信配置错误");
        }

        SmsResponse smsResponse = smsBlend.sendMessage(phone, templateId, params);
        log.info("短信发送，手机号：{}，模板ID：{}，参数：{}，响应：{}", phone, templateId, params, smsResponse);

        return smsResponse.isSuccess();
    }

}
