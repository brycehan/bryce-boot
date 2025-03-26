package com.brycehan.boot.bpm.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceCopyDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceCopyPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessInstanceCopy;
import jakarta.validation.constraints.NotEmpty;
import org.flowable.bpmn.model.FlowNode;

import java.util.Collection;

/**
 * 流程抄送服务
 * <br>
 * 现在是在审批的时候进行流程抄送
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
public interface BpmProcessInstanceCopyService extends BaseService<BpmProcessInstanceCopy> {

    /**
     * 添加流程抄送
     *
     * @param bpmProcessInstanceCopyDto 流程抄送Dto
     */
    void save(BpmProcessInstanceCopyDto bpmProcessInstanceCopyDto);

    /**
     * 更新流程抄送
     *
     * @param bpmProcessInstanceCopyDto 流程抄送Dto
     */
    void update(BpmProcessInstanceCopyDto bpmProcessInstanceCopyDto);

    /**
     * 流程抄送分页查询
     *
     * @param bpmProcessInstanceCopyPageDto 查询条件
     * @return 分页信息
     */
    PageResult<BpmProcessInstanceCopy> page(BpmProcessInstanceCopyPageDto bpmProcessInstanceCopyPageDto);

    /**
     * 【管理员】流程实例的抄送
     *
     * @param userIds 抄送的用户编号
     * @param reason 抄送意见
     * @param taskId 流程任务编号
     */
    void createProcessInstanceCopy(Collection<Long> userIds, String reason, String taskId);

    /**
     * 【自动抄送】流程实例的抄送
     *
     * @param userIds 抄送的用户编号
     * @param reason 抄送意见
     * @param processInstanceId 流程编号
     * @param activityId 流程活动编号（对应 {@link FlowNode#getId()}）
     * @param activityName 任务编号（对应 {@link FlowNode#getName()}）
     * @param taskId 任务编号，允许空
     */
    void createProcessInstanceCopy(Collection<Long> userIds, String reason,
                                   @NotEmpty(message = "流程实例编号不能为空") String processInstanceId,
                                   @NotEmpty(message = "流程活动编号不能为空") String activityId,
                                   @NotEmpty(message = "流程活动名称不能为空") String activityName,
                                   String taskId);

    /**
     * 删除抄送流程
     *
     * @param processInstanceId 流程实例 ID
     */
    void deleteProcessInstanceCopy(String processInstanceId);

}
