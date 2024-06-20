package com.brycehan.boot.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 排序项数据传输对象
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "排序项Dto")
public class OrderItemDto extends BaseDto {

    /**
     * 需要进行排序的字段
     */
    @Schema(description = "需要进行排序的字段")
    @NotEmpty(message = "排序的字段不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{1,64}$", message = "排序的字段只能是字母、数字和下划线，且长度在1-64字符")
    private String column;

    /**
     * 是否正序排列，默认 false
     */
    @Schema(description = "是否正序排列，默认 false")
    private boolean asc;

}
