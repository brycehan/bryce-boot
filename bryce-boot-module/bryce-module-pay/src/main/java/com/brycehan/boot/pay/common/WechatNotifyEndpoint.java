package com.brycehan.boot.pay.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 微信通知端点
 *
 * @author Bryce Han
 * @since 2024/3/9
 */
@Getter
@RequiredArgsConstructor
public enum WechatNotifyEndpoint {

    /**
     * 微信Native支付通知
     */
    WECHAT_NATIVE_PAY_NOTIFY("/pay/wechatPay/pay/notify"),
    /**
     * 微信JSAPI支付通知
     */
    WECHAT_JSAPI_PAY_NOTIFY("/pay/wechatPay/pay/notify"),

    /**
     * 退款结果通知
     */
    REFUND_NOTIFY("/pay/wechatPay/refund/notify");

    /**
     * 端点
     */
    private final String endpoint;

}
