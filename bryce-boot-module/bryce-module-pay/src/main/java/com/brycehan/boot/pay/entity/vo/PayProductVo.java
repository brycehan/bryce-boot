package com.brycehan.boot.pay.entity.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品 Vo
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@Schema(description = "商品Vo")
public class PayProductVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private Long id;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String title;

    /**
     * 价格（分）
     */
    @Schema(description = "价格（分）")
    private Integer price;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime createdTime;

}
