package com.brycehan.boot.sms.service.impl;

import com.brycehan.boot.common.base.RedisKeys;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Value("${sms.number_text_messages_sent_per_day}")
    private int numberTextMessagesSentPerDay = 5;

    private final RedisTemplate<String, Integer> redisTemplate;

    @Override
    public boolean send(String phone, String templateId, LinkedHashMap<String, String> params) {
        SmsBlend smsBlend = SmsFactory.getSmsBlend("sms1");

        if (smsBlend == null) {
            throw new RuntimeException("短信配置错误");
        }

        // 判断是否超过发送次数
        String smsTodayCountKey = RedisKeys.getSmsTodayCountKey(phone);
        Integer smsTodayCount = this.redisTemplate.opsForValue().get(smsTodayCountKey);
        if (smsTodayCount != null && smsTodayCount >= numberTextMessagesSentPerDay) {
            throw new ServerException("短信发送次数超过限制，请明天再试");
        }

        SmsResponse smsResponse = smsBlend.sendMessage(phone, templateId, params);
        log.info("短信发送，手机号：{}，模板ID：{}，参数：{}，响应：{}", phone, templateId, params, smsResponse);

        // 记录当前手机号码每天短信发送次数
        if (smsTodayCount == null) {
            smsTodayCount = 0;
        }
        this.redisTemplate.opsForValue().set(smsTodayCountKey, ++smsTodayCount, 24, TimeUnit.HOURS);

        return smsResponse.isSuccess();
    }

}
