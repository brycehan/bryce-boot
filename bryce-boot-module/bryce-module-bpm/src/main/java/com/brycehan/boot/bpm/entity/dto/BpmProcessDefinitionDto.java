package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 流程定义信息Dto
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程定义Dto")
public class BpmProcessDefinitionDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 流程定义的编号
     */
    @Schema(description = "流程定义的编号")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String processDefinitionId;

    /**
     * 流程模型的编号
     */
    @Schema(description = "流程模型的编号")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String modelId;

    /**
     * 流程模型的类型
     */
    @Schema(description = "流程模型的类型")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String modelType;

    /**
     * 图标
     */
    @Schema(description = "图标")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String icon;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Length(max = 1000, groups = {SaveGroup.class, UpdateGroup.class})
    private String formConf;

    /**
     * 表单项的数组
     */
    @Schema(description = "表单项的数组")
    @Size(max = 500, groups = {SaveGroup.class, UpdateGroup.class})
    private List<String> formFields;

    /**
     * 自定义表单的提交路径
     */
    @Schema(description = "自定义表单的提交路径")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String formCustomCreatePath;

    /**
     * 自定义表单的查看路径
     */
    @Schema(description = "自定义表单的查看路径")
    @Length(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String formCustomViewPath;

    /**
     * Simple设计器模型数据
     */
    @Schema(description = "Simple设计器模型数据")
    @Length(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Length(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
    private String startUserIds;

    /**
     * 可处理人
     */
    @Schema(description = "可处理人")
    @Length(max = 65535, groups = {SaveGroup.class, UpdateGroup.class})
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
    @Valid
    private BpmModelMetaInfoDto.ProcessIdRule processIdRule;

    /**
     * 自动审批类型
     */
    @Schema(description = "自动审批类型")
    private Integer autoApprovalType;

    /**
     * 标题设置
     */
    @Schema(description = "标题设置")
    @Valid
    private BpmModelMetaInfoDto.TitleSetting titleSetting;

    /**
     * 摘要设置
     */
    @Schema(description = "摘要设置")
    @Valid
    private BpmModelMetaInfoDto.SummarySetting summarySetting;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}
