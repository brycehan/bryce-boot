package com.brycehan.boot.pay.entity.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 支付记录Vo
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@Schema(description = "支付记录Vo")
public class PayPaymentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 支付记录ID
     */
    @Schema(description = "支付记录ID")
    private Long id;

    /**
     * 商户订单编号
     */
    @Schema(description = "商户订单编号")
    private String orderNo;

    /**
     * 支付系统交易编号
     */
    @Schema(description = "支付系统交易编号")
    private String transactionId;

    /**
     * 支付类型
     */
    @Schema(description = "支付类型")
    private String paymentType;

    /**
     * 交易类型
     */
    @Schema(description = "交易类型")
    private String tradeType;

    /**
     * 交易状态
     */
    @Schema(description = "交易状态")
    private String tradeState;

    /**
     * 支付金额(分)
     */
    @Schema(description = "支付金额(分)")
    private Integer payerTotal;

    /**
     * 通知参数
     */
    @Schema(description = "通知参数")
    private String content;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}
