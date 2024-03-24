package com.brycehan.boot.pay.enums;

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

    WECHAT_PAY("微信"),
    ALI_PAY("支付宝");

    /**
     * 类型
     */
    private final String value;

}
