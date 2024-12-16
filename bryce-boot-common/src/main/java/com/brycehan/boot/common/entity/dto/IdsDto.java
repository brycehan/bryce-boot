package com.brycehan.boot.common.entity.dto;

import com.brycehan.boot.common.base.validator.NotEmptyElements;
import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * ids数据传输对象
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "IdsDto")
public class IdsDto extends BaseDto {

    /**
     * IDs
     */
    @Schema(description = "ids")
    @Size(min = 1, max = 1000)
    @NotNull
    @NotEmptyElements
    private List<Long> ids;

}
