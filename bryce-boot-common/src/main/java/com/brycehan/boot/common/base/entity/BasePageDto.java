package com.brycehan.boot.common.base.entity;

import com.brycehan.boot.common.util.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 基础分页Dto
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Data
public abstract class BasePageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 当前页数（从1开始计算）
     */
    @Schema(description = "当前页数（从1开始计算）")
    @Range(min = 1, message = "页数最小值为1")
    @NotNull(message = "页码不能为空")
    private Integer current;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数")
    @Range(min = 1, max = 1000, message = "每页条数，取值范围在1-1000")
    @NotNull(message = "每页条数不能为空")
    private Integer size;

    /**
     * 排序项
     */
    @Schema(description = "排序项")
    @Valid
    private List<OrderItemDto> orderItems;

    /**
     * 转换为JSON字符串
     *
     * @return JSON字符串
     */
    public String toJson(){
        return JsonUtils.writeValueAsString(this);
    }

}
