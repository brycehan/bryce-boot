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
 * 支付记录Dto
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "支付记录Dto")
public class PayPaymentDto extends BaseDto {

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
     * 支付系统交易编号
     */
    @Schema(description = "支付系统交易编号")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String transactionId;

    /**
     * 支付类型
     */
    @Schema(description = "支付类型")
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String paymentType;

    /**
     * 交易类型
     */
    @Schema(description = "交易类型")
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String tradeType;

    /**
     * 交易状态
     */
    @Schema(description = "交易状态")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Size(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
    private String content;

}
