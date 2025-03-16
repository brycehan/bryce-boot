package com.brycehan.boot.bpm.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 审批详情 Vo
 *
 * @since 2025/3/11
 * @author Bryce Han
 */
@Data
@Accessors(chain = true)
@Schema(description = "审批详情 Vo")
public class BpmApprovalDetailVo {

    @Schema(description = "流程实例的状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status; // 参见 BpmProcessInstanceStatusEnum 枚举

    @Schema(description = "活动节点列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ActivityNode> activityNodes;

    @Schema(description = "表单字段权限")
    private Map<String, String> formFieldsPermission;

    @Schema(description = "待办任务")
    private BpmTaskVo todoTask;

    /**
     * 所属流程定义信息
     */
    private BpmProcessDefinitionVo processDefinition;

    /**
     * 所属流程实例信息
     */
    private BpmProcessInstanceVo processInstance;

    @Accessors(chain = true)
    @Schema(description = "活动节点信息")
    @Data
    public static class ActivityNode {

        @Schema(description = "节点编号")
        private String id;

        @Schema(description = "节点名称")
        private String name;

        @Schema(description = "节点类型")
        private Integer nodeType; // 参见 BpmSimpleModelNodeType 枚举

        @Schema(description = "节点状态")
        private Integer status; // 参见 BpmTaskStatusEnum 枚举

        @Schema(description = "节点的开始时间")
        private LocalDateTime startTime;
        @Schema(description = "节点的结束时间")
        private LocalDateTime endTime;

        @Schema(description = "审批节点的任务信息")
        private List<ActivityNodeTask> tasks;

        @Schema(description = "候选人策略", example = "35")
        private Integer candidateStrategy; // 参见 BpmTaskCandidateStrategyEnum 枚举。主要用于发起时，审批节点、抄送节点自选

        @Schema(description = "候选人用户 ID 列表")
        @JsonIgnore // 不返回，只是方便后续读取，赋值给 candidateUsers
        private List<Long> candidateUserIds;

        @Schema(description = "候选人用户列表")
        private List<UserSimpleBaseVo> candidateUsers; // 只包含未生成 ApprovalTaskInfo 的用户列表

    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "活动节点的任务信息")
    public static class ActivityNodeTask {

        @Schema(description = "任务编号")
        private String id;

        @Schema(description = "任务所属人编号")
        @JsonIgnore // 不返回，只是方便后续读取，赋值给 ownerUser
        private Long owner;

        @Schema(description = "任务所属人", example = "1024")
        private UserSimpleBaseVo ownerUser;

        @Schema(description = "任务分配人编号")
        @JsonIgnore // 不返回，只是方便后续读取，赋值给 assigneeUser
        private Long assignee;

        @Schema(description = "任务分配人", example = "2048")
        private UserSimpleBaseVo assigneeUser;

        @Schema(description = "任务状态")
        private Integer status;  // 参见 BpmTaskStatusEnum 枚举

        @Schema(description = "审批意见", example = "同意")
        private String reason;

        @Schema(description = "签名")
        private String signPicUrl;
    }

}
