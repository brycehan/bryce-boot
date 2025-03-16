package com.brycehan.boot.bpm.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 通过流程任务的 Dto
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Data
@Accessors(chain = true)
@Schema(description = "通过流程任务的 Dto")
public class BpmTaskApproveDto {

    @Schema(description = "任务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "任务编号不能为空")
    private String id;

    @Schema(description = "审批意见", example = "不错不错！")
    private String reason;

    @Schema(description = "签名")
    private String signPicUrl;

    @Schema(description = "变量实例（动态表单）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, Object> variables;

}
