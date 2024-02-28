package com.brycehan.boot.pay.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.io.Serial;

/**
 * 支付记录Dto
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@Schema(description = "支付记录 Dto")
public class PayPaymentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}
