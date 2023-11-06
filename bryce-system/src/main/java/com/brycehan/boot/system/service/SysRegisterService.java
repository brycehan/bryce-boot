package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.dto.RegisterDto;

/**
 * 注册服务类
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
public interface SysRegisterService {

    /**
     * 注册
     *
     * @param registerDto 注册数据传输对象
     */
    void register(RegisterDto registerDto);

    /**
     * 校验验证码
     *
     * @param key  唯一标识key
     * @param code 验证码
     * @return 校验结果（true：正确，false：错误）
     */
    boolean validate(String key, String code);

    /**
     * 获取注册图片验证码开关
     *
     * @return 开启标识（true：开启，false：关闭）
     */
    boolean isCaptchaEnabled();

}
