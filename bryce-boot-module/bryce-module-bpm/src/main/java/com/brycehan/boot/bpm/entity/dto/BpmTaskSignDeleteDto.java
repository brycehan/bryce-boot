package com.brycehan.boot.bpm.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 加签任务的删除（减签） Dto
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Data
@Schema(description = "加签任务的删除（减签） Dto")
public class BpmTaskSignDeleteDto {

    @Schema(description = "被减签的任务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "任务编号不能为空")
    private String id;

    @Schema(description = "加签原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "需要减签")
    @NotEmpty(message = "加签原因不能为空")
    private String reason;

}
