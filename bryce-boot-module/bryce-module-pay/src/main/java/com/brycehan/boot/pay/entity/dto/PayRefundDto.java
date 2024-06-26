package com.brycehan.boot.pay.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 退款单Dto
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "退款单Dto")
public class PayRefundDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 商户订单编号
     */
    @Schema(description = "商户订单编号")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String orderNo;

    /**
     * 商户退款单编号
     */
    @Schema(description = "商户退款单编号")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String refundNo;

    /**
     * 支付系统退款单号
     */
    @Schema(description = "支付系统退款单号")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String reason;

    /**
     * 退款状态
     */
    @Schema(description = "退款状态")
    @Size(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private String refundStatus;

    /**
     * 申请退款返回参数
     */
    @Schema(description = "申请退款返回参数")
    @Size(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
    private String contentReturn;

    /**
     * 退款结果通知参数
     */
    @Schema(description = "退款结果通知参数")
    @Size(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
    private String contentNotify;

}
