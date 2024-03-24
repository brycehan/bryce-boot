package com.brycehan.boot.pay.vo;

import cn.hutool.core.date.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 退款单 Vo
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@Schema(description = "退款单 Vo")
public class PayRefundVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 退款单ID
     */
    @Schema(description = "退款单ID")
    private Long id;

    /**
     * 商户订单编号
     */
    @Schema(description = "商户订单编号")
    private String orderNo;

    /**
     * 商户退款单编号
     */
    @Schema(description = "商户退款单编号")
    private String refundNo;

    /**
     * 支付系统退款单号
     */
    @Schema(description = "支付系统退款单号")
    private String refundId;

    /**
     * 原订单金额(分)
     */
    @Schema(description = "原订单金额(分)")
    private Integer totalFee;

    /**
     * 退款金额(分)
     */
    @Schema(description = "退款金额(分)")
    private Integer refund;

    /**
     * 退款原因
     */
    @Schema(description = "退款原因")
    private String reason;

    /**
     * 退款状态
     */
    @Schema(description = "退款状态")
    private String refundStatus;

    /**
     * 申请退款返回参数
     */
    @Schema(description = "申请退款返回参数")
    private String contentReturn;

    /**
     * 退款结果通知参数
     */
    @Schema(description = "退款结果通知参数")
    private String contentNotify;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}
