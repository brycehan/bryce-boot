package com.brycehan.boot.system.security;

import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.framework.security.phone.PhoneCodeValidateService;
import com.brycehan.boot.system.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 手机验证码校验服务实现
 *
 * @author Bryce Han
 * @since 2023/10/9
 */
@Service
@RequiredArgsConstructor
public class PhoneCodeValidateServiceImpl implements PhoneCodeValidateService {

    private final SmsService smsService;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean validate(String phone, String code) {

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
}
