package com.brycehan.boot.sms.controller;

import com.brycehan.boot.api.sms.SmsApi;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.sms.service.SmsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

/**
 * 短信控制器
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "短信", description = "sms")
@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
public class SmsApiController implements SmsApi {

    private final SmsService smsService;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean send(String phone, String templateId, LinkedHashMap<String, String> params) {
        return this.smsService.send(phone, templateId, params);
    }

    @Override
    public Boolean verifyCode(String phone, String templateId, String code) {
        // 如果关闭了短信功能，则直接校验通过
        if (!this.smsService.isSmsEnabled()) {
            return true;
        }

        // 获取缓存验证码
        String codeKey = CacheConstants.SMS_CODE_KEY.concat("login").concat(":").concat(phone);
        String codeValue = this.stringRedisTemplate.opsForValue()
                .getAndDelete(codeKey);

        // 校验
        return code.equalsIgnoreCase(codeValue);
    }

    @Override
    public boolean isSmsEnabled() {
        return this.smsService.isSmsEnabled();
    }
}
