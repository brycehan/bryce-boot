package com.brycehan.boot.pay.entity.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单Vo
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Data
@Schema(description = "订单Vo")
public class PayOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private Long id;

    /**
     * 订单标题
     */
    @Schema(description = "订单标题")
    private String title;

    /**
     * 商户订单编号
     */
    @Schema(description = "商户订单编号")
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
    private String codeUrl;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private String orderStatus;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createdTime;

}
