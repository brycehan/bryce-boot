package com.brycehan.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.base.RedisKeys;
import com.brycehan.boot.framework.common.config.properties.CaptchaProperties;
import com.brycehan.boot.system.service.CaptchaService;
import com.brycehan.boot.system.service.SysParamService;
import com.brycehan.boot.system.vo.CaptchaVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现
 *
 * @author Bryce Han
 * @since 2023/10/4
 */
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private final StringRedisTemplate stringRedisTemplate;

    private final SysParamService sysParamService;

    private final CaptchaProperties captchaProperties;

    @Override
    public CaptchaVo generate() {
        // 生成验证码 key
        String key = UUID.randomUUID().toString();

        // 生成验证码
        SpecCaptcha captcha = new SpecCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
        captcha.setLen(captchaProperties.getLength());
        captcha.setCharType(Captcha.TYPE_DEFAULT);

        String captchaKey = RedisKeys.getCaptchaKey(key);
        String captchaValue = captcha.text();

        // 存储到 Redis
        this.stringRedisTemplate.opsForValue()
                .set(captchaKey, captchaValue, captchaProperties.getExpiration(), TimeUnit.MINUTES);

        // 封装返回数据
        CaptchaVo captchaVo = new CaptchaVo();
        captchaVo.setKey(key);
        captchaVo.setImage(captcha.toBase64());

        return captchaVo;
    }

    @Override
    public boolean validate(String key, String code) {
        // 如果关闭了验证码，则直接校验通过
        if (!isCaptchaEnabled()) {
            return true;
        }

        if (StrUtil.isBlank(key) || StrUtil.isBlank(code)) {
            return false;
        }

        // 获取缓存验证码
        String captchaKey = RedisKeys.getCaptchaKey(key);
        String captchaValue = this.stringRedisTemplate.opsForValue()
                .getAndDelete(captchaKey);

        // 校验
        return code.equalsIgnoreCase(captchaValue);
    }

    @Override
    public boolean isCaptchaEnabled() {
        return this.sysParamService.getBoolean("system.account.captchaEnabled");
    }
}
