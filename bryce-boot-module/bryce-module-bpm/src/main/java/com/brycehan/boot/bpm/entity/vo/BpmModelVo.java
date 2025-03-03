package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程定义信息Vo
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程定义信息Vo")
public class BpmModelVo extends BpmModelMetaInfoVo {

    /**
     * 编号
     */
    @Schema(description = "编号")
    private String id;

    /**
     * 流程标识
     */
    @Schema(description = "流程标识")
    private String key;

    /**
     * 流程名称
     */
    @Schema(description = "流程名称")
    private String name;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

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
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String formName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * BPMN XML
     */
    @Schema(description = "BPMN XML")
    private String bpmnXml;

    @Schema(description = "仿钉钉流程设计模型对象")
    private BpmSimpleModelNodeVo simpleModel;

    /**
     * 最新部署的流程定义
     */
    @Schema(description = "最新部署的流程定义")
    private BpmProcessDefinitionVo processDefinition;

}
