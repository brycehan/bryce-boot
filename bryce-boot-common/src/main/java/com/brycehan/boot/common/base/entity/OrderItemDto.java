package com.brycehan.boot.common.base.entity;

import com.brycehan.boot.common.util.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 排序项Dto 数据传输对象
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Data
@Builder
@Schema(description = "排序项Dto")
public class OrderItemDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 需要进行排序的字段
     */
    @Schema(description = "需要进行排序的字段")
    @NotEmpty(message = "排序的字段不能为空")
    private String column;

    /**
     * 是否正序排列，默认 false
     */
    @Schema(description = "是否正序排列，默认 false")
    private boolean asc;

    public String toString(){
        return JsonUtils.writeValueAsString(this);
    }

}
