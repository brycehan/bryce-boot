package com.brycehan.boot.bpm.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * BPM 发送任务被分配 Dto
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Accessors(chain = true)
@Data
@Schema(description = "BPM 发送任务被分配 Dto")
public class BpmMessageSendWhenTaskCreatedDto {

    /**
     * 流程实例的编号
     */
    @NotEmpty
    @Schema(description = "流程实例的编号")
    private String processInstanceId;
    /**
     * 流程实例的名字
     */
    @NotEmpty
    @Schema(description = "流程实例的名字")
    private String processInstanceName;
    /**
     * 发起人的用户编号
     */
    @NotNull
    @Schema(description = "发起人的用户编号")
    private Long startUserId;

    /**
     * 发起人的昵称
     */
    @Schema(description = "发起人的昵称")
    @NotEmpty
    private String startUserNickname;

    /**
     * 流程任务的编号
     */
    @NotEmpty
    @Schema(description = "流程任务的编号")
    private String taskId;
    /**
     * 流程任务的名字
     */
    @NotEmpty
    @Schema(description = "流程任务的名字")
    private String taskName;

    /**
     * 审批人的用户编号
     */
    @NotNull
    @Schema(description = "审批人的用户编号")
    private Long assigneeUserId;

}
