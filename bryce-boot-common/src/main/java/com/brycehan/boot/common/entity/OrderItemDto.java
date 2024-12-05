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
     * 排序的字段
     */
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]{1,64}$", message = "只能是字母、数字，长度为1~64字符")
    @Schema(description = "排序的字段")
    private String column;

    /**
     * 是否正序排列，默认 false
     */
    @Schema(description = "是否正序排列，默认 false")
    private boolean asc;

}
