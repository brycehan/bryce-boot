package com.brycehan.boot.bpm.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Collection;

/**
 * 抄送流程任务的 Dto
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Data
@Schema(description = "抄送流程任务的 Dto")
public class BpmTaskCopyDto {

    @Schema(description = "任务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "任务编号不能为空")
    private String id;

    @Schema(description = "抄送的用户编号数组", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1,2]")
    @NotEmpty(message = "抄送用户不能为空")
    private Collection<Long> copyUserIds;

    @Schema(description = "抄送意见", example = "帮忙看看！")
    private String reason;
}
