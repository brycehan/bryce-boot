package com.brycehan.boot.bpm.service;

import cn.hutool.core.collection.CollUtil;
import com.brycehan.boot.bpm.entity.dto.BpmApprovalDetailDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceCancelDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstancePageDto;
import com.brycehan.boot.bpm.entity.vo.BpmApprovalDetailVo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessInstanceBpmnModelViewVo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessInstanceVo;
import com.brycehan.boot.common.entity.PageResult;
import jakarta.validation.Valid;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 流程实例服务
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
public interface BpmProcessInstanceService {

    /**
     * 创建流程实例
     *
     * @param userId      用户编号
     * @param bpmProcessInstanceDto 创建信息
     * @return 实例的编号
     */
    String createProcessInstance(Long userId, @Valid BpmProcessInstanceDto bpmProcessInstanceDto);

    /**
     * 获取流程实例
     *
     * @param processInstanceId 流程实例的编号
     * @return 流程实例
     */
    ProcessInstance getProcessInstance(String processInstanceId);

    /**
     * 获得历史的流程实例
     *
     * @param processInstanceId 流程实例的编号
     * @return 历史的流程实例
     */
    HistoricProcessInstance getHistoricProcessInstance(String processInstanceId);

    /**
     * 获得流程实例
     *
     * @param processInstanceId 流程实例的编号
     * @return 流程实例
     */
    BpmProcessInstanceVo getById(String processInstanceId);

    /**
     * 获取审批详情。
     * <p>
     * 可以是准备发起的流程、进行中的流程、已经结束的流程
     *
     * @param userId  登录人的用户编号
     * @param bpmApprovalDetailDto 请求信息
     * @return 流程实例的进度
     */
    BpmApprovalDetailVo getApprovalDetail(Long userId, BpmApprovalDetailDto bpmApprovalDetailDto);

    /**
     * 获得流程实例列表
     *
     * @param processInstanceIds 流程实例的编号集合
     * @return 流程实例列表
     */
    List<ProcessInstance> getProcessInstances(List<String> processInstanceIds);

    /**
     * 获得流程实例 Map
     *
     * @param processInstanceIds 流程实例的编号集合
     * @return 流程实例列表 Map
     */
    default Map<String, ProcessInstance> getProcessInstanceMap(List<String> processInstanceIds) {
        if (CollUtil.isEmpty(processInstanceIds)) {
            return Map.of();
        }
        return getProcessInstances(processInstanceIds).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(ProcessInstance::getProcessInstanceId, Function.identity()));
    }

    /**
     * 获得历史的流程实例列表
     *
     * @param processInstanceIds 流程实例的编号集合
     * @return 历史的流程实例列表
     */
    List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> processInstanceIds);

    /**
     * 获得历史的流程实例 Map
     *
     * @param processInstanceIds 流程实例的编号集合
     * @return 历史的流程实例列表 Map
     */
    default Map<String, HistoricProcessInstance> getHistoricProcessInstanceMap(Set<String> processInstanceIds) {
        if (CollUtil.isEmpty(processInstanceIds)) {
            return Map.of();
        }
        return getHistoricProcessInstances(processInstanceIds).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(HistoricProcessInstance::getId, Function.identity()));
    }

    /**
     * 获得流程实例的分页
     *
     * @param userId    用户编号
     * @param pageDto 分页请求
     * @return 流程实例的分页
     */
    PageResult<HistoricProcessInstance> getProcessInstancePage(Long userId, BpmProcessInstancePageDto pageDto);

    /**
     * 获取流程实例的 BPMN 模型视图
     *
     * @param id 流程实例的编号
     * @return BPMN 模型视图
     */
    BpmProcessInstanceBpmnModelViewVo getProcessInstanceBpmnModelView(String id);

    /**
     * 发起人取消流程实例
     *
     * @param userId      用户编号
     * @param cancelDto 取消信息
     */
    void cancelProcessInstanceByStartUser(Long userId, BpmProcessInstanceCancelDto cancelDto);

    /**
     * 管理员取消流程实例
     *
     * @param userId      用户编号
     * @param cancelDto 取消信息
     */
    void cancelProcessInstanceByAdmin(Long userId, BpmProcessInstanceCancelDto cancelDto);

    /**
     * 更新 ProcessInstance 为不通过
     *
     * @param processInstance 流程实例
     * @param reason          理由。例如说，审批不通过时，需要传递该值
     */
    void updateProcessInstanceReject(ProcessInstance processInstance, String reason);

    /**
     * 处理 ProcessInstance 完成事件，例如说：审批通过、不通过、取消
     *
     * @param instance 流程任务
     */
    void processProcessInstanceCompleted(ProcessInstance instance);

    /**
     * 更新 ProcessInstance 的变量
     *
     * @param id 流程编号
     * @param variables 流程变量
     */
    void updateProcessInstanceVariables(String id, Map<String, Object> variables);

    /**
     * 管理员分页查询流程实例
     *
     * @param bpmProcessInstancePageDto 分页请求
     * @return 流程实例分页
     */
    PageResult<BpmProcessInstanceVo> managerPage(BpmProcessInstancePageDto bpmProcessInstancePageDto);

    /**
     * 我的分页查询流程实例
     *
     * @param bpmProcessInstancePageDto 分页请求
     * @return 流程实例分页
     */
    PageResult<BpmProcessInstanceVo> myPage(BpmProcessInstancePageDto bpmProcessInstancePageDto);

}
