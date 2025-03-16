package com.brycehan.boot.bpm.entity.vo;

import cn.hutool.core.lang.Pair;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 流程任务 Vo
 *
 * @since 2025/3/11
 * @author Bryce Han
 */
@Data
@Accessors(chain = true)
@Schema(description = "流程任务 Vo")
public class BpmTaskVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "任务编号")
    private String id;

    @Schema(description = "任务名字")
    private String name;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "持续时间", example = "1000")
    private Long durationInMillis;

    @Schema(description = "任务状态")
    private Integer status; // 参见 BpmTaskStatusEnum 枚举

    @Schema(description = "审批理由")
    private String reason;

    @Schema(description = "任务负责人编号")
    @JsonIgnore // 不返回，只是方便后续读取，赋值给 ownerUser
    private Long owner;
    /**
     * 负责人的用户信息
     */
    private UserSimpleBaseVo ownerUser;

    @Schema(description = "任务分配人编号")
    @JsonIgnore // 不返回，只是方便后续读取，赋值给 assigneeUser
    private Long assignee;
    /**
     * 审核的用户信息
     */
    private UserSimpleBaseVo assigneeUser;

    @Schema(description = "任务定义的标识")
    private String taskDefinitionKey;

    @Schema(description = "所属流程实例编号")
    private String processInstanceId;
    /**
     * 所属流程实例
     */
    private ProcessInstance processInstance;

    @Schema(description = "父任务编号")
    private String parentTaskId;
    @Schema(description = "子任务列表（由加签生成）")
    private List<BpmTaskVo> children; // 由加签生成，包含多层子任务

    @Schema(description = "表单编号", example = "1024")
    private Long formId;
    @Schema(description = "表单名字", example = "请假表单")
    private String formName;
    @Schema(description = "表单的配置，JSON 字符串")
    private String formConf;
    @Schema(description = "表单项的数组")
    private List<String> formFields;
    @Schema(description = "提交的表单值")
    private Map<String, Object> formVariables;
    @Schema(description = "操作按钮设置值")
    private Map<Integer, OperationButtonSetting> buttonsSetting;

    @Schema(description = "是否需要签名", example = "false")
    private Boolean signEnable;

    @Schema(description = "是否填写审批意见", example = "false")
    private Boolean reasonRequire;

    @Data
    @Schema(description = "流程实例")
    public static class ProcessInstance {

        @Schema(description = "流程实例编号")
        private String id;

        @Schema(description = "流程实例名称")
        private String name;

        @Schema(description = "提交时间")
        private LocalDateTime createTime;

        @Schema(description = "流程定义的编号")
        private String processDefinitionId;

        @Schema(description = "流程摘要", example = "[]")
        private List<Pair<String, String>> summary; // 只有流程表单，才有摘要！

        /**
         * 发起人的用户信息
         */
        private UserSimpleBaseVo startUser;

    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "操作按钮设置")
    public static class OperationButtonSetting {

        @Schema(description = "显示名称", example = "审批")
        private String displayName;

        @Schema(description = "是否启用", example = "true")
        private Boolean enable;
    }

}
