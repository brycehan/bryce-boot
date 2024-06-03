package com.brycehan.boot.pay.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 支付记录entity
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_pay_payment")
public class PayPayment extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商户订单编号
     */
    private String orderNo;

    /**
     * 支付系统交易编号
     */
    private String transactionId;

    /**
     * 支付类型
     */
    private String paymentType;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 交易状态
     */
    private String tradeState;

    /**
     * 支付金额(分)
     */
    private Integer payerTotal;

    /**
     * 通知参数
     */
    private String content;

}
