package com.brycehan.boot.bpm.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.brycehan.boot.bpm.entity.vo.BpmModelMetaInfoVo;
import com.brycehan.boot.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 流程定义信息entity
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "brc_bpm_process_definition_info", autoResultMap = true)
public class BpmProcessDefinitionInfo extends BaseEntity {

    /**
     * 流程定义的编号
     */
    private String processDefinitionId;

    /**
     * 流程模型的编号
     */
    private String modelId;

    /**
     * 流程模型的类型
     */
    private String modelType;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
     * 表单类型
     */
    private Integer formType;

    /**
     * 表单编号
     */
    private Long formId;

    /**
     * 表单的配置
     */
    private String formConf;

    /**
     * 表单项的数组
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> formFields;

    /**
     * 自定义表单的提交路径
     */
    private String formCustomCreatePath;

    /**
     * 自定义表单的查看路径
     */
    private String formCustomViewPath;

    /**
     * Simple设计器模型数据
     */
    private String simpleModel;

    /**
     * 可见范围
     */
    private Integer visible;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 可发起人
     */
    private String startUserIds;

    /**
     * 可处理人
     */
    private String managerUserIds;

    /**
     * 允许取消正在运行的流程
     */
    private Integer allowCancelRunningProcess;

    /**
     * 流程编号规则
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private BpmModelMetaInfoVo.ProcessIdRule processIdRule;

    /**
     * 自动审批类型
     */
    private Integer autoApprovalType;

    /**
     * 标题设置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private BpmModelMetaInfoVo.TitleSetting titleSetting;

    /**
     * 摘要设置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private BpmModelMetaInfoVo.SummarySetting summarySetting;

    /**
     * 租户编号
     */
    private Long tenantId;
}
