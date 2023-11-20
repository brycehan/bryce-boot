package com.brycehan.boot.sms.service.impl;

import com.brycehan.boot.api.system.SysParamApi;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

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

    private final SysParamApi sysParamApi;

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${sms.expire}")
    private Long expire;

    @Override
    public boolean send(String phone, String templateId, LinkedHashMap<String, String> params) {
        SmsBlend smsBlend = SmsFactory.getSmsBlend("sms1");

        if (smsBlend == null) {
            throw new RuntimeException("短信配置错误");
        }

        SmsResponse smsResponse = smsBlend.sendMessage(phone, templateId, params);
        log.debug("短信发送，手机号：{}，响应：{}", phone, smsResponse);
        if(smsResponse.isSuccess()) {
            String codeKey = CacheConstants.SMS_CODE_KEY.concat(templateId).concat(":").concat(phone);
            String code = params.get("code");
            // 存储到Redis
            this.stringRedisTemplate.opsForValue()
                    .set(codeKey, code, this.expire, TimeUnit.MINUTES);
        }
        return smsResponse.isSuccess();
    }

    @Override
    public boolean isSmsEnabled() {
        return this.sysParamApi.getBoolean("system.sms.enabled");
    }

}
