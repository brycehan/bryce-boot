package com.brycehan.boot.pay.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 支付类型
 *
 * @author Bryce Han
 * @since 2024/3/1
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PayType {

    WECHAT_PAY("微信Native支付"),
    WECHAT_JSAPI_PAY("微信小程序支付"),
    ALI_PAY("支付宝");

    /**
     * 类型
     */
    private final String value;

}
