package com.brycehan.boot.bpm.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.entity.BaseEntity;
import lombok.experimental.Accessors;
import org.flowable.bpmn.model.FlowNode;

/**
 * 流程抄送entity
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("brc_bpm_process_instance_copy")
public class BpmProcessInstanceCopy extends BaseEntity {

    /**
     * 发起流程的用户编号
     */
    private Long startUserId;

    /**
     * 流程实例的名称
     */
    private String processInstanceName;

    /**
     * 流程实例的编号
     */
    private String processInstanceId;

    /**
     * 流程定义编号
     */
    private String processDefinitionId;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 流程活动的编号
     * <p/>
     *
     * 冗余 {@link FlowNode#getId()}，对应 BPMN XML 节点编号
     * 原因：用于查询抄送节点的表单字段权限。因为仿钉钉/飞书的抄送节点 (ServiceTask)，没有 taskId，只有 activityId
     */
    private String activityId;

    /**
     * 流程活动的名称
     * <br/>
     * 冗余 {@link FlowNode#getName()}
     */
    private String activityName;

    /**
     * 任务主键
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 用户编号（被抄送的用户编号）
     */
    private Long userId;

    /**
     * 抄送意见
     */
    private String reason;

    /**
     * 租户编号
     */
    private Long tenantId;

}
