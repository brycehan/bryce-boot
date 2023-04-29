package com.brycehan.boot.api.controller;

import cn.hutool.captcha.AbstractCaptcha;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.property.CaptchaProperties;
import com.brycehan.boot.system.service.SysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码控制器
 *
 * @author Bryce Han
 * @since 2022/5/10
 */
@Slf4j
@Tag(name = "captcha", description = "验证码API")
@RequestMapping("/captcha")
@RestController
public class CaptchaController {

    private final CaptchaProperties captchaProperties;

    private final AbstractCaptcha captcha;

    private final SysConfigService sysConfigService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public CaptchaController(SysConfigService sysConfigService, AbstractCaptcha captcha, CaptchaProperties captchaProperties) {
        this.captchaProperties = captchaProperties;
        this.captcha = captcha;
        this.sysConfigService = sysConfigService;
    }

    /**
     * 生成验证码
     *
     * @return 响应结果
     */
    @Operation(summary = "生成验证码")
    @GetMapping
    public ResponseResult<?> captcha() {
        boolean captchaEnabled = this.sysConfigService.selectCaptchaEnabled();
        Map<String, Object> data = new HashMap<>();
        data.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return ResponseResult.ok(data);
        }
        String uuid = UUID.randomUUID().toString();
        // 重新生成验证码
        captcha.createCode();
        String captchaKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captchaValue = this.captcha.getCode().toLowerCase();
        // 存储到Redis
        this.stringRedisTemplate.opsForValue()
                .set(captchaKey, captchaValue, captchaProperties.getExpiration(), TimeUnit.MINUTES);
        data.put("uuid", uuid);
        data.put("image", captcha.getImageBase64());
        return ResponseResult.ok(data);

    }

}
