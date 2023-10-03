package com.brycehan.boot.system.controller;

import com.brycehan.boot.system.service.SysParamService;
import com.brycehan.boot.system.service.SysSmsService;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.property.CaptchaProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 短信控制器
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "sms", description = "短信API")
@RequestMapping("/sms")
@RestController
public class SmsController {

    private final CaptchaProperties captchaProperties;

    private final SysParamService sysParamService;

    private final SysSmsService sysSmsService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public SmsController(CaptchaProperties captchaProperties, SysParamService sysParamService, SysSmsService sysSmsService) {
        this.captchaProperties = captchaProperties;
        this.sysParamService = sysParamService;
        this.sysSmsService = sysSmsService;
    }

    /**
     * 生成注册验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成注册验证码")
    @GetMapping(path = "/code/register")
    public ResponseResult<?> register(String phoneNumber) {
        boolean smsEnabled = this.sysParamService.selectSmsEnabled();
        Map<String, Object> data = new HashMap<>();
        data.put("smsEnabled", smsEnabled);
        if (!smsEnabled) {
            return ResponseResult.ok(data);
        }
        String uuid = UUID.randomUUID().toString();
        String captchaKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captchaValue = RandomStringUtils.randomNumeric(6);
        this.sysSmsService.sendSms(phoneNumber,
                "【XXXX】您的注册验证码是".concat(captchaValue).concat("，5分钟内有效，请勿泄露。"));
        // 存储到Redis
        this.stringRedisTemplate.opsForValue()
                .set(captchaKey, captchaValue, captchaProperties.getExpiration(), TimeUnit.MINUTES);
        data.put("uuid", uuid);
        return ResponseResult.ok(data);
    }

}
