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
 * 商品Dto
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商品Dto")
public class PayProductDto extends BaseDto {

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
