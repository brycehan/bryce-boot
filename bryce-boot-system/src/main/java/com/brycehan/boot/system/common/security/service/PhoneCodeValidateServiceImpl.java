package com.brycehan.boot.system.common.security.service;

import com.brycehan.boot.common.enums.SmsType;
import com.brycehan.boot.framework.security.phone.PhoneCodeValidateService;
import com.brycehan.boot.system.service.AuthSmsService;
import lombok.RequiredArgsConstructor;
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

    private final AuthSmsService authSmsService;

    @Override
    public boolean validate(String phone, String code) {
        return this.authSmsService.validate(phone, code, SmsType.LOGIN);
    }

}
