package com.brycehan.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.base.RedisKeys;
import com.brycehan.boot.common.constant.ParamConstants;
import com.brycehan.boot.common.enums.CaptchaType;
import com.brycehan.boot.framework.common.config.properties.CaptchaProperties;
import com.brycehan.boot.framework.security.TokenUtils;
import com.brycehan.boot.system.entity.vo.CaptchaVo;
import com.brycehan.boot.system.service.AuthCaptchaService;
import com.brycehan.boot.system.service.SysParamService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
public class AuthCaptchaServiceImpl implements AuthCaptchaService {

    private final StringRedisTemplate stringRedisTemplate;

    private final SysParamService sysParamService;

    private final CaptchaProperties captchaProperties;

    @Override
    public CaptchaVo generate() {
        // 生成验证码 key
        String uuid = TokenUtils.uuid();

        // 生成验证码
        SpecCaptcha captcha = new SpecCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
        captcha.setLen(captchaProperties.getLength());
        captcha.setCharType(Captcha.TYPE_DEFAULT);

        String captchaKey = RedisKeys.getCaptchaKey(uuid);
        String captchaValue = captcha.text();

        log.debug("图片验证码uuid：{}, 值：{}", uuid, captchaValue);

        // 存储到 Redis
        this.stringRedisTemplate.opsForValue()
                .set(captchaKey, captchaValue, captchaProperties.getExpiration(), TimeUnit.MINUTES);

        // 封装返回数据
        CaptchaVo captchaVo = new CaptchaVo();
        captchaVo.setUuid(uuid);
        captchaVo.setImage(captcha.toBase64());

        return captchaVo;
    }

    @Override
    public boolean validate(String key, String code, CaptchaType captchaType) {
        // 如果关闭了验证码，则直接校验通过
        if (!captchaEnabled(captchaType)) {
            return true;
        }

        if (StrUtil.isBlank(key) || StrUtil.isBlank(code)) {
            return false;
        }

        // 获取缓存验证码
        String captchaKey = RedisKeys.getCaptchaKey(key);
        String captchaValue = this.stringRedisTemplate.opsForValue()
                .get(captchaKey);

        // 校验
        boolean validated = code.equalsIgnoreCase(captchaValue);
        if (validated) {
            // 删除验证码
            this.stringRedisTemplate.delete(captchaKey);
        }

        return validated;
    }

    @Override
    public boolean captchaEnabled(CaptchaType captchaType) {
        if (captchaType == null) {
            return false;
        }

        if (CaptchaType.LOGIN.equals(captchaType)) {
            return this.sysParamService.getBoolean(ParamConstants.SYSTEM_LOGIN_CAPTCHA_ENABLED);
        }
        if (CaptchaType.REGISTER.equals(captchaType)) {
            return this.sysParamService.getBoolean(ParamConstants.SYSTEM_REGISTER_CAPTCHA_ENABLED);
        }

        return false;
    }
}
