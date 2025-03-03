package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 流程定义 Vo
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@Schema(description = "流程定义 Vo")
public class BpmProcessDefinitionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long id;

    /**
     * 版本
     */
    @Schema(description = "版本")
    private Integer version;

    /**
     * 流程名称
     */
    @Schema(description = "流程名称")
    private String name;

    /**
     * 流程标识
     */
    @Schema(description = "流程标识")
    private String key;

    /**
     * 流程图标
     */
    @Schema(description = "流程图标")
    private String icon;

    /**
     * 描述
     */
    @Schema(description = "流程描述")
    private String description;

    /**
     * 流程分类
     */
    @Schema(description = "流程分类")
    private String category;

    /**
     * 流程分类名称
     */
    @Schema(description = "流程分类名称")
    private String categoryName;

    /**
     * 流程模型的类型
     */
    @Schema(description = "流程模型的类型")
    private Integer modelType;

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
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String formName;

    /**
     * 表单的配置
     */
    @Schema(description = "表单的配置")
    private String formConf;

    /**
     * 表单项的数组
     */
    @Schema(description = "表单项的数组")
    private List<String> formFields;

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
     * 中断状态
     */
    @Schema(description = "中断状态")
    private Integer suspensionState;

    /**
     * 部署时间
     */
    @Schema(description = "部署时间")
    private LocalDateTime deploymentTime; // 需要从对应的 Deployment 读取，非必须返回

    /**
     * BPMN XML
     */
    @Schema(description = "BPMN XML")
    private String bpmnXml; // 需要从对应的 BpmnModel 读取，非必须返回

    /**
     * SIMPLE 设计器模型数据 json 格式
     */
    @Schema(description = "SIMPLE 设计器模型数据")
    private String simpleModel; // 非必须返回

    /**
     * 流程定义排序
     */
    @Schema(description = "流程定义排序")
    private Long sort;

    /**
     * BPMN UserTask 用户任务
     */
    @Data
    @Schema(description = "BPMN UserTask 用户任务")
    public static class UserTask {

        /**
         * 任务标识
         */
        @Schema(description = "任务标识")
        private String id;

        /**
         * 任务名称
         */
        @Schema(description = "任务名称")
        private String name;
    }
}
