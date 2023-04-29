package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

/**
 * 删除数据传输对象
 *
 * @author Bryce Han
 * @since 2022/10/31
 */
@Getter
@Setter
@Schema(description = "删除Dto")
public class DeleteDto extends BaseDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * IDs
     */
    @Schema(description = "IDs")
    @Size(min = 1, max = 10000)
    @NotNull
    private List<String> ids;

}
