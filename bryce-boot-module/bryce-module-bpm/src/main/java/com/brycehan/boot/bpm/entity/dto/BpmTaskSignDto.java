package com.brycehan.boot.bpm.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

/**
 * 加签任务的创建（加签） Dto
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Data
@Schema(description = "加签任务的创建（加签） Dto")
public class BpmTaskSignDto {

    @Schema(description = "需要加签的任务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "任务编号不能为空")
    private String id;

    @Schema(description = "加签的用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "888")
    @NotEmpty(message = "加签用户不能为空")
    private Set<Long> userIds;

    @Schema(description = "加签类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "before")
    @NotEmpty(message = "加签类型不能为空")
    private String type; // 参见 BpmTaskSignTypeEnum 枚举

    @Schema(description = "加签原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "需要加签")
    @NotEmpty(message = "加签原因不能为空")
    private String reason;

}
