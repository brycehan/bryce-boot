package com.brycehan.boot.pay.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 支付宝交易状态
 *
 * @author Bryce Han
 * @since 2024/3/20
 */
@Getter
@RequiredArgsConstructor
public enum AlipayTradeState {
    /**
     * 交易支付成功
     */
    SUCCESS("TRADE_SUCCESS"),
    /**
     * 交易结束，不可退款
     */
    FINISHED("TRADE_FINISHED"),
    /**
     * 交易创建，等待买家付款
     */
    NO_PAY("WAIT_BUYER_PAY"),
    /**
     * 交易关闭
     */
    CLOSED("TRADE_CLOSED"),
    /**
     * 退款成功
     */
    REFUND_SUCCESS("REFUND_SUCCESS"),
    /**
     * 退款失败
     */
    REFUND_ERROR("REFUND_ERROR");

    /**
     * 值
     */
    private final String value;

}
