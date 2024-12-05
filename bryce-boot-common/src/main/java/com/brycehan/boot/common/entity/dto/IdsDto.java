package com.brycehan.boot.common.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 删除数据传输对象
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "删除Dto")
public class IdsDto extends BaseDto {

    /**
     * IDs
     */
    @Schema(description = "IDs")
    @Size(min = 1, max = 1000)
    @NotNull
    private List<Long> ids;

}
