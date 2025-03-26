package com.brycehan.boot.bpm.entity.vo;

import cn.hutool.core.lang.Pair;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 流程抄送Vo
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@Schema(description = "流程抄送Vo")
public class BpmProcessInstanceCopyVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long id;

    /**
     * 发起人
     */
    @Schema(description = "发起人")
    private BpmUserSimpleBaseVo startUser;

    /**
     * 流程实例的编号
     */
    @Schema(description = "流程实例的编号")
    private String processInstanceId;

    /**
     * 流程实例的名称
     */
    @Schema(description = "流程实例的名称")
    private String processInstanceName;

    /**
     * 流程定义的编号
     */
    @Schema(description = "流程定义的编号")
    private String processDefinitionId;

    /**
     * 流程实例的发起时间
     */
    @Schema(description = "流程实例的发起时间")
    private LocalDateTime processInstanceStartTime;

    /**
     * 流程分类
     */
    @Schema(description = "流程分类")
    private String category;

    /**
     * 流程活动的编号
     */
    @Schema(description = "流程活动的编号")
    private String activityId;

    /**
     * 流程活动的名称
     */
    @Schema(description = "流程活动的名称")
    private String activityName;

    /**
     * 任务主键
     */
    @Schema(description = "任务主键")
    private String taskId;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String taskName;

    /**
     * 抄送人意见
     */
    @Schema(description = "抄送人意见")
    private String reason;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private BpmUserSimpleBaseVo createUser;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 流程摘要
     */
    @Schema(description = "流程摘要", example = "[]")
    private List<Pair<String, String>> summary;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}
