package com.brycehan.boot.common.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 删除数据传输对象
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Data
@Schema(description = "删除Dto")
public class IdsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * IDs
     */
    @Schema(description = "IDs")
    @Size(min = 1, max = 1000, message = "ID有效个数在1-1000")
    @NotNull
    private List<Long> ids;

}
