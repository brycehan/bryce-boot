package com.brycehan.boot.pay.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 退款单entity
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_pay_refund")
public class PayRefund extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商户订单编号
     */
    private String orderNo;

    /**
     * 商户退款单编号
     */
    private String refundNo;

    /**
     * 支付系统退款单号
     */
    private String refundId;

    /**
     * 原订单金额(分)
     */
    private Integer totalFee;

    /**
     * 退款金额(分)
     */
    private Integer refund;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 退款状态
     */
    private String refundStatus;

    /**
     * 申请退款返回参数
     */
    private String contentReturn;

    /**
     * 退款结果通知参数
     */
    private String contentNotify;

}
