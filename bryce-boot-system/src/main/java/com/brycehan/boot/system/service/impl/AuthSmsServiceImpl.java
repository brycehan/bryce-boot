package com.brycehan.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.api.sms.SmsApi;
import com.brycehan.boot.api.system.SysParamApi;
import com.brycehan.boot.common.base.RedisKeys;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.constant.ParamConstants;
import com.brycehan.boot.common.enums.SmsType;
import com.brycehan.boot.common.util.RegexUtils;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.service.AuthSmsService;
import com.brycehan.boot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import static com.brycehan.boot.common.constant.CacheConstants.SMS_CODE_TTL;

/**
 * 验证码服务实现
 *
 * @author Bryce Han
 * @since 2023/10/4
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthSmsServiceImpl implements AuthSmsService {

    private final StringRedisTemplate stringRedisTemplate;

    private final SysParamApi sysParamApi;

    private final SmsApi smsApi;

    private final SysUserService sysUserService;

    @Override
    public void sendCode(String phone, SmsType smsType) {

        if (!this.smsEnabled()) {
            throw new RuntimeException("短信功能未开启");
        }

        if (!this.smsEnabled(smsType)) {
            throw new RuntimeException(smsType.getDesc() + "短信功能未开启");
        }

        if (!RegexUtils.isPhoneValid(phone)) {
            throw new ServerException("手机号码格式错误");
        }

        SysUser sysUser = this.sysUserService.getByPhone(phone);
        if (SmsType.LOGIN.equals(smsType)) {
            if(sysUser == null) {
                throw new ServerException("手机号码未注册");
            }
        }

        String smsCodeKey = RedisKeys.getSmsCodeKey(phone, smsType);
        String smsCodeValue = this.stringRedisTemplate.opsForValue()
                .get(smsCodeKey);

        // 生成6位验证码
        if (StrUtil.isEmpty(smsCodeValue)) {
            smsCodeValue = RandomStringUtils.randomNumeric(6);
        }

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("code", smsCodeValue);

        // 发送短信
        Boolean send = this.smsApi.send(phone, smsType, params);
        if (!send) {
            throw new ServerException("短信发送失败");
        }

        log.debug("短信验证码手机号码：{}, 值：{}", phone, smsCodeValue);

        // 存储到 Redis
        this.stringRedisTemplate.opsForValue()
                .set(smsCodeKey, smsCodeValue, SMS_CODE_TTL, TimeUnit.MINUTES);
    }

    @Override
    public boolean validate(String phone, String code, SmsType smsType) {
        // 如果关闭了验证码，则直接校验通过
        if (!smsEnabled(smsType)) {
            return true;
        }

        if (StrUtil.isBlank(phone) || StrUtil.isBlank(code)) {
            return false;
        }

        // 获取缓存验证码
        String smsCodeKey = RedisKeys.getSmsCodeKey(phone, smsType);
        String smsCodeValue = this.stringRedisTemplate.opsForValue()
                .get(smsCodeKey);

        // 校验
        boolean validated = code.equalsIgnoreCase(smsCodeValue);
        if (validated) {
            // 删除验证码
            this.stringRedisTemplate.delete(smsCodeKey);
        }

        return validated;
    }

    @Override
    public boolean smsEnabled() {
        return this.sysParamApi.getBoolean(ParamConstants.SYSTEM_SMS_ENABLED);
    }

    @Override
    public boolean smsEnabled(SmsType smsType) {
        if (smsType == null) {
            return false;
        }
        if (SmsType.LOGIN.equals(smsType)) {
            return this.sysParamApi.getBoolean(ParamConstants.SYSTEM_LOGIN_SMS_ENABLED);
        } else if (SmsType.REGISTER.equals(smsType)) {
            return this.sysParamApi.getBoolean(ParamConstants.SYSTEM_REGISTER_SMS_ENABLED);
        }

        return false;
    }
}
