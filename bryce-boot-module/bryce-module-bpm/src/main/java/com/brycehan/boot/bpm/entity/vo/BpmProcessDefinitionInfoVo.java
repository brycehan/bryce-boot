package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 流程定义信息Vo
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@Schema(description = "流程定义信息Vo")
public class BpmProcessDefinitionInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long id;

    /**
     * 流程定义的编号
     */
    @Schema(description = "流程定义的编号")
    private String processDefinitionId;

    /**
     * 流程模型的编号
     */
    @Schema(description = "流程模型的编号")
    private String modelId;

    /**
     * 流程模型的类型
     */
    @Schema(description = "流程模型的类型")
    private String modelType;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 表单类型
     */
    @Schema(description = "表单类型")
    private Integer formType;

    /**
     * 表单编号
     */
    @Schema(description = "表单编号")
    private Long formId;

    /**
     * 表单的配置
     */
    @Schema(description = "表单的配置")
    private String formConf;

    /**
     * 表单项的数组
     */
    @Schema(description = "表单项的数组")
    private String formFields;

    /**
     * 自定义表单的提交路径
     */
    @Schema(description = "自定义表单的提交路径")
    private String formCustomCreatePath;

    /**
     * 自定义表单的查看路径
     */
    @Schema(description = "自定义表单的查看路径")
    private String formCustomViewPath;

    /**
     * Simple设计器模型数据
     */
    @Schema(description = "Simple设计器模型数据")
    private String simpleModel;

    /**
     * 可见范围
     */
    @Schema(description = "可见范围")
    private Integer visible;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Long sort;

    /**
     * 可发起人
     */
    @Schema(description = "可发起人")
    private String startUserIds;

    /**
     * 可处理人
     */
    @Schema(description = "可处理人")
    private String managerUserIds;

    /**
     * 允许取消正在运行的流程
     */
    @Schema(description = "允许取消正在运行的流程")
    private Integer allowCancelRunningProcess;

    /**
     * 流程编号规则
     */
    @Schema(description = "流程编号规则")
    private String processIdRule;

    /**
     * 自动审批类型
     */
    @Schema(description = "自动审批类型")
    private Integer autoApprovalType;

    /**
     * 标题设置
     */
    @Schema(description = "标题设置")
    private String titleSetting;

    /**
     * 摘要设置
     */
    @Schema(description = "摘要设置")
    private String summarySetting;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}
