package com.brycehan.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.api.sms.SmsApi;
import com.brycehan.boot.api.system.SysParamApi;
import com.brycehan.boot.common.base.RedisKeys;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.vo.SmsCodeVo;
import com.brycehan.boot.common.constant.ParamConstants;
import com.brycehan.boot.common.enums.SmsType;
import com.brycehan.boot.framework.security.TokenUtils;
import com.brycehan.boot.system.entity.vo.SysUserVo;
import com.brycehan.boot.system.service.AuthSmsService;
import com.brycehan.boot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

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

    private final long expiration = 5;

    @Override
    public SmsCodeVo sendCode(String phone, SmsType smsType) {
        if (!this.smsEnabled()) {
            throw new RuntimeException("短信功能未开启");
        }

        if (!this.smsEnabled(smsType)) {
            throw new RuntimeException(smsType.desc() + "短信功能未开启");
        }

        SysUserVo sysUserVo = this.sysUserService.getByPhone(phone);
        if(sysUserVo == null) {
            throw new ServerException("手机号码未注册");
        }

        // 生成验证码 key
        String codeKey = TokenUtils.uuid();

        // 生成6位验证码
        String codeValue = RandomStringUtils.randomNumeric(6);

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("code", codeValue);

        // 发送短信
        Boolean sended = this.smsApi.send(phone, smsType, params);
        if (!sended) {
            throw new ServerException("短信发送失败");
        }

        log.debug("短信验证码key：{}, 值：{}", codeKey, codeValue);

        String smsCodeKey = RedisKeys.getSmsCodeKey(codeKey, smsType);

        // 存储到 Redis
        this.stringRedisTemplate.opsForValue()
                .set(smsCodeKey, codeValue, this.expiration, TimeUnit.MINUTES);

        // 封装返回数据
        SmsCodeVo smsCodeVo = new SmsCodeVo();
        smsCodeVo.setKey(codeKey);
        return smsCodeVo;
    }

    @Override
    public boolean validate(String key, String code, SmsType smsType) {
        // 如果关闭了验证码，则直接校验通过
        if (!smsEnabled(smsType)) {
            return true;
        }

        if (StrUtil.isBlank(key) || StrUtil.isBlank(code)) {
            return false;
        }

        // 获取缓存验证码
        String smsCodeKey = RedisKeys.getSmsCodeKey(key, smsType);
        String captchaValue = this.stringRedisTemplate.opsForValue()
                .getAndDelete(smsCodeKey);

        // 校验
        return code.equalsIgnoreCase(captchaValue);
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
