package com.brycehan.boot.bpm.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 退回流程任务的 Dto
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Accessors(chain = true)
@Data
@Schema(description = "不通过流程任务的 Dto")
public class BpmTaskRejectDto {

    @Schema(description = "任务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "任务编号不能为空")
    private String id;

    @Schema(description = "审批意见", requiredMode = Schema.RequiredMode.REQUIRED, example = "不错不错！")
    private String reason;

}
