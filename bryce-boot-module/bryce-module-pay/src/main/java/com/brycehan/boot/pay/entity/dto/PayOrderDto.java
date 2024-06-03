package com.brycehan.boot.pay.entity.dto;

import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 订单Dto
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Data
@Schema(description = "订单Dto")
public class PayOrderDto implements Serializable {

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
     * 订单标题
     */
    @Schema(description = "订单标题")
    @Size(max = 256, groups = {SaveGroup.class, UpdateGroup.class})
    private String title;

    /**
     * 商户订单编号
     */
    @Schema(description = "商户订单编号")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String orderNo;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 支付产品id
     */
    @Schema(description = "支付产品id")
    private Long productId;

    /**
     * 订单金额(分)
     */
    @Schema(description = "订单金额(分)")
    private Integer totalFee;

    /**
     * 订单二维码连接
     */
    @Schema(description = "订单二维码连接")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String codeUrl;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    @Size(max = 10, groups = {SaveGroup.class, UpdateGroup.class})
    private String orderStatus;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private String remark;

}
