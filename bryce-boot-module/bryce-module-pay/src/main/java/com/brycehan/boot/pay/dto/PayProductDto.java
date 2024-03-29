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
 * 商品Dto
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@Schema(description = "商品 Dto")
public class PayProductDto implements Serializable {

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
     * 商品名称
     */
    @Schema(description = "商品名称")
    @Size(max = 20, groups = {SaveGroup.class, UpdateGroup.class})
    private String title;

    /**
     * 价格（分）
     */
    @Schema(description = "价格（分）")
    private Integer price;

}
