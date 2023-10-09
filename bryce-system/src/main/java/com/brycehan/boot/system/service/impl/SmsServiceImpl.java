package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.system.service.SmsService;
import com.brycehan.boot.system.service.SysParamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 短信服务实现
 *
 * @since 2023/10/8
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final SysParamService sysParamService;

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${sms.expire}")
    private Long expire;

    @Override
    public boolean send(String phone, String templateId, LinkedHashMap<String, String> params) {
        SmsBlend smsBlend = SmsFactory.getSmsBlend("sms1");

        if(smsBlend == null) {
            throw new RuntimeException("短信配置错误");
        }

        SmsResponse smsResponse = smsBlend.sendMessage(phone, templateId, params);
        log.debug("短信发送，手机号：{}，响应：{}", phone, smsResponse);

        return smsResponse.isSuccess();
    }

    @Override
    public ResponseResult<?> send(String phone, String type, String templateId, LinkedHashMap<String, String> params) {
        boolean smsEnabled = this.isSmsEnabled();
        Map<String, Object> data = new HashMap<>();
        data.put("smsEnabled", smsEnabled);
        if (!smsEnabled) {
            return ResponseResult.ok(data);
        }

        String codeKey = CacheConstants.SMS_CODE_KEY.concat(type).concat(":").concat(phone);
        String code = params.get("code");

        this.send(phone, templateId, params);

        // 存储到Redis
        this.stringRedisTemplate.opsForValue()
                .set(codeKey, code, this.expire, TimeUnit.MINUTES);

        return ResponseResult.ok();
    }

    @Override
    public boolean isSmsEnabled() {
        return this.sysParamService.getBoolean("system.sms.enabled");
    }

}
