package com.brycehan.boot.common.base.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * 基础分页 DTO 数据传输对象
 *
 * @author Bryce Han
 * @since 2021/8/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BasePageDto extends BaseEntity {

    /**
     * 起始页数，从1开始计算
     */
    @Schema(description = "起始页数，从1开始计算")
    @Range(min = 1)
    @NotNull(message = "页码不能为空")
    private Integer current;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数")
    @Range(min = 1)
    @NotNull(message = "每页条数不能为空")
    private Integer pageSize;

    /**
     * 排序项
     */
    @Schema(description = "排序项")
    @Valid
    private List<OrderItemDto> orderItems;

}
