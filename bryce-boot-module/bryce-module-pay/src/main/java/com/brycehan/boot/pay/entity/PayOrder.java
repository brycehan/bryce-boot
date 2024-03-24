package com.brycehan.boot.pay.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.base.entity.BaseEntity;
import java.io.Serial;

/**
 * 订单entity
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_pay_order")
public class PayOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单标题
     */
    private String title;

    /**
     * 商户订单编号
     */
    private String orderNo;

    /**
     * 支付类型
     */
    private String paymentType;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 支付产品id
     */
    private Long productId;

    /**
     * 订单金额(分)
     */
    private Integer totalFee;

    /**
     * 订单二维码连接
     */
    private String codeUrl;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 备注
     */
    private String remark;

}
