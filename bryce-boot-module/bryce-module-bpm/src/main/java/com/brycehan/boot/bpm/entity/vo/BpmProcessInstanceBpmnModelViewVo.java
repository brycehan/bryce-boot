package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 * 流程示例的 BPMN 视图 Response VO
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Data
@Accessors(chain = true)
@Schema(description = "流程示例的 BPMN 视图 Response VO")
public class BpmProcessInstanceBpmnModelViewVo {

    // 基本信息

    @Schema(description = "流程实例信息")
    private BpmProcessInstanceVo processInstance;

    @Schema(description = "任务列表")
    private List<BpmTaskVo> tasks;

    @Schema(description = "BPMN XML")
    private String bpmnXml;

    @Schema(description = "SIMPLE 模型")
    private BpmSimpleModelNodeVo simpleModel;

    // 进度信息

    @Schema(description = "进行中的活动节点编号集合")
    private Set<String> unfinishedTaskActivityIds; // 只包括 UserTask

    @Schema(description = "已经完成的活动节点编号集合")
    private Set<String> finishedTaskActivityIds; // 包括 UserTask、Gateway 等，不包括 SequenceFlow

    @Schema(description = "已经完成的连线节点编号集合")
    private Set<String> finishedSequenceFlowActivityIds; // 只包括 SequenceFlow

    @Schema(description = "已经拒绝的活动节点编号集合")
    private Set<String> rejectedTaskActivityIds; // 只包括 UserTask

}
