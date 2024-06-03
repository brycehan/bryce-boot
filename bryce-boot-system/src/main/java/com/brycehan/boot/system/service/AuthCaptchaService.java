package com.brycehan.boot.system.service;

import com.brycehan.boot.common.enums.CaptchaType;
import com.brycehan.boot.system.vo.CaptchaVo;

/**
 * 验证码服务
 *
 * @author Bryce Han
 * @since 2023/10/4
 */
public interface AuthCaptchaService {

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    CaptchaVo generate();

    /**
     * 校验验证码
     *
     * @param key  key
     * @param code 验证码
     * @return 校验结果（true：正确，false：错误）
     */
    boolean validate(String key, String code, CaptchaType captchaType);

    /**
     * 获取登录图片验证码开关
     *
     * @param captchaType 验证码类型
     * @return 开启标识（true：开启，false：关闭）
     */
    boolean captchaEnabled(CaptchaType captchaType);

}
