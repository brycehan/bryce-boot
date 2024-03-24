package com.brycehan.boot.pay.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Bryce Han
 * @since 2024/3/9
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum WechatNotifyEndpoint {

    /**
     * 支付通知
     */
    NATIVE_NOTIFY("/pay/wechatPay/native/notify"),

    /**
     * 退款结果通知
     */
    REFUND_NOTIFY("/pay/wechatPay/refund/notify");

    /**
     * 端点
     */
    private final String endpoint;

}
