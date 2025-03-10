package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程定义PageDto
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@Schema(description = "流程定义PageDto")
@EqualsAndHashCode(callSuper = true)
public class BpmProcessDefinitionPageDto extends BasePageDto {

    /**
     * 流程标识
     */
    @Schema(description = "流程标识")
    private String key;

}
