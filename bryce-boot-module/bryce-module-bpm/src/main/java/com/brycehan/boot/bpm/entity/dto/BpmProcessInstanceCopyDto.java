package com.brycehan.boot.bpm.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import org.flowable.bpmn.model.FlowNode;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程抄送Dto
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程抄送Dto")
public class BpmProcessInstanceCopyDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @Null(groups = SaveGroup.class)
    @NotNull(groups = UpdateGroup.class)
    private Long id;

    /**
     * 发起流程的用户编号
     */
    @Schema(description = "发起流程的用户编号")
    private Long startUserId;

    /**
     * 流程实例的名称
     */
    @Schema(description = "流程实例的名称")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String processInstanceName;

    /**
     * 流程实例的编号
     */
    @Schema(description = "流程实例的编号")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String processInstanceId;

    /**
     * 流程定义编号
     */
    @Schema(description = "流程定义编号")
    private String processDefinitionId;

    /**
     * 流程分类
     */
    @Schema(description = "流程分类")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String category;

    /**
     * 流程活动的编号
     * <p/>
     *
     * 冗余 {@link FlowNode#getId()}，对应 BPMN XML 节点编号
     * 原因：用于查询抄送节点的表单字段权限。因为仿钉钉/飞书的抄送节点 (ServiceTask)，没有 taskId，只有 activityId
     */
    @Schema(description = "流程活动的编号")
    private String activityId;

    /**
     * 流程活动的名称
     * <br/>
     * 冗余 {@link FlowNode#getName()}
     */
    @Schema(description = "流程活动的名称")
    private String activityName;

    /**
     * 任务主键
     */
    @Schema(description = "任务主键")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String taskId;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    @Length(max = 64, groups = {SaveGroup.class, UpdateGroup.class})
    private String taskName;

    /**
     * 用户编号（被抄送的用户编号）
     */
    @Schema(description = "用户编号（被抄送的用户编号）")
    private Long userId;

    /**
     * 抄送意见
     */
    @Schema(description = "抄送意见")
    private String reason;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}
