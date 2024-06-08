package com.brycehan.boot.sms.api;

import com.brycehan.boot.api.sms.SmsApi;
import com.brycehan.boot.common.enums.SmsType;
import com.brycehan.boot.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 短信 Api 实现
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsApiService implements SmsApi {

    private final SmsService smsService;
    private final Environment environment;

    @Override
    public Boolean send(String phone, SmsType smsType, LinkedHashMap<String, String> params) {
        String templateIdKey = "sms." + smsType.value() + "-template-id";
        String templateId = this.environment.getProperty(templateIdKey);
        return this.smsService.send(phone, templateId, params);
    }
}
