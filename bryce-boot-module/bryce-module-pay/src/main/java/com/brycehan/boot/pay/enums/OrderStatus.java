package com.brycehan.boot.pay.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Bryce Han
 * @since 2024/3/1
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {

    NO_PAY("未支付"),
    SUCCESS("支付成功"),
    CLOSED("超时已关闭"),
    CANCEL("用户已取消"),
    REFUND_PROCESSING("退款中"),
    REFUND_SUCCESS("已退款"),
    REFUND_ABNORMAL("退款异常");

    /**
     * 类型
     */
    private final String type;

}
