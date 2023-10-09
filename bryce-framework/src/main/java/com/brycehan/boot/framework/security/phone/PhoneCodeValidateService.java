package com.brycehan.boot.framework.security.phone;

/**
 * 手机短信登录，验证码校验
 *
 * @since 2023/10/7
 * @author Bryce Han
 */
public interface PhoneCodeValidateService {

    /**
     * 校验手机验证码
     *
     * @param phone 手机号
     * @param code 验证码
     * @return 校验结果（true：通过，false：拒绝）
     */
    boolean validate(String phone, String code);

}
