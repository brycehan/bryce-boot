package com.brycehan.boot.bpm.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 流程实例的取消 Dto
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Data
@Schema(description = "流程实例的取消 Dto")
public class BpmProcessInstanceCancelDto {

    @Schema(description = "流程实例的编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "流程实例的编号不能为空")
    private String id;

    @Schema(description = "取消原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "不请假了！")
    @NotEmpty(message = "取消原因不能为空")
    private String reason;

}
