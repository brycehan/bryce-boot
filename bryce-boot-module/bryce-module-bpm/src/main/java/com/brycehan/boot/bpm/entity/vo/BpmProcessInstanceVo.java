package com.brycehan.boot.bpm.entity.vo;

import cn.hutool.core.lang.Pair;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 流程实例的 Vo
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Accessors(chain = true)
@Data
@Schema(description = "流程实例的 Vo")
public class BpmProcessInstanceVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "流程实例的编号")
    private String id;

    @Schema(description = "流程名称")
    private String name;

    @Schema(description = "流程摘要")
    private List<Pair<String, String>> summary; // 只有流程表单，才有摘要！

    @Schema(description = "流程分类")
    private String category;
    @Schema(description = "流程分类名称")
    private String categoryName;

    @Schema(description = "流程实例的状态")
    private Integer status; // 参见 BpmProcessInstanceStatusEnum 枚举

    @Schema(description = "发起时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "持续时间", example = "1000")
    private Long durationInMillis;

    @Schema(description = "提交的表单值")
    private Map<String, Object> formVariables;

    @Schema(description = "业务的唯一标识-例如说，请假申请的编号")
    private String businessKey;

    /**
     * 发起流程的用户
     */
    private UserSimpleBaseVo startUser;

    @Schema(description = "流程定义的编号")
    private String processDefinitionId;
    /**
     * 流程定义
     */
    private BpmProcessDefinitionVo processDefinition;

    /**
     * 当前审批中的任务
     */
    private List<Task> tasks; // 仅在流程实例分页才返回

    @Schema(description = "流程任务")
    @Data
    public static class Task {

        @Schema(description = "流程任务的编号")
        private String id;

        @Schema(description = "任务名称")
        private String name;

    }

}
