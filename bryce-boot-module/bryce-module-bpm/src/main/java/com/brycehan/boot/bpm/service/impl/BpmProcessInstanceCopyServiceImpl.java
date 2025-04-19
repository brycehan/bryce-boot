package com.brycehan.boot.bpm.service.impl;

import java.util.Collection;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionService;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import com.brycehan.boot.bpm.service.BpmTaskService;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.BpmResponseStatus;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.bpm.entity.convert.BpmProcessInstanceCopyConvert;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceCopyDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceCopyPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessInstanceCopy;
import com.brycehan.boot.bpm.service.BpmProcessInstanceCopyService;
import com.brycehan.boot.bpm.mapper.BpmProcessInstanceCopyMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import java.util.Objects;

import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


/**
 * 流程抄送服务实现
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmProcessInstanceCopyServiceImpl extends BaseServiceImpl<BpmProcessInstanceCopyMapper, BpmProcessInstanceCopy> implements BpmProcessInstanceCopyService {

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmTaskService taskService;

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmProcessInstanceService processInstanceService;
    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmProcessDefinitionService processDefinitionService;

    /**
     * 添加流程抄送
     *
     * @param bpmProcessInstanceCopyDto 流程抄送Dto
     */
    public void save(BpmProcessInstanceCopyDto bpmProcessInstanceCopyDto) {
        BpmProcessInstanceCopy bpmProcessInstanceCopy = BpmProcessInstanceCopyConvert.INSTANCE.convert(bpmProcessInstanceCopyDto);
        bpmProcessInstanceCopy.setId(IdGenerator.nextId());
        baseMapper.insert(bpmProcessInstanceCopy);
    }

    /**
     * 更新流程抄送
     *
     * @param bpmProcessInstanceCopyDto 流程抄送Dto
     */
    public void update(BpmProcessInstanceCopyDto bpmProcessInstanceCopyDto) {
        BpmProcessInstanceCopy bpmProcessInstanceCopy = BpmProcessInstanceCopyConvert.INSTANCE.convert(bpmProcessInstanceCopyDto);
        baseMapper.updateById(bpmProcessInstanceCopy);
    }

    @Override
    public PageResult<BpmProcessInstanceCopy> page(BpmProcessInstanceCopyPageDto bpmProcessInstanceCopyPageDto) {
        IPage<BpmProcessInstanceCopy> page = baseMapper.selectPage(bpmProcessInstanceCopyPageDto.toPage(), getWrapper(bpmProcessInstanceCopyPageDto));
        return PageResult.of(page.getRecords(), page.getTotal());
    }

    /**
     * 封装查询条件
     *
     * @param bpmProcessInstanceCopyPageDto 流程抄送分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<BpmProcessInstanceCopy> getWrapper(BpmProcessInstanceCopyPageDto bpmProcessInstanceCopyPageDto){
        LambdaQueryWrapper<BpmProcessInstanceCopy> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(bpmProcessInstanceCopyPageDto.getUserId() != null, BpmProcessInstanceCopy::getUserId, bpmProcessInstanceCopyPageDto.getUserId());
        wrapper.eq(Objects.nonNull(bpmProcessInstanceCopyPageDto.getTenantId()), BpmProcessInstanceCopy::getTenantId, bpmProcessInstanceCopyPageDto.getTenantId());

        if(bpmProcessInstanceCopyPageDto.getCreatedTimeStart() != null && bpmProcessInstanceCopyPageDto.getCreatedTimeEnd() != null) {
            wrapper.between(BpmProcessInstanceCopy::getCreatedTime, bpmProcessInstanceCopyPageDto.getCreatedTimeStart(), bpmProcessInstanceCopyPageDto.getCreatedTimeEnd());
        } else if(bpmProcessInstanceCopyPageDto.getCreatedTimeStart() != null) {
            wrapper.ge(BpmProcessInstanceCopy::getCreatedTime, bpmProcessInstanceCopyPageDto.getCreatedTimeStart());
        }else if(bpmProcessInstanceCopyPageDto.getCreatedTimeEnd() != null) {
            wrapper.ge(BpmProcessInstanceCopy::getCreatedTime, bpmProcessInstanceCopyPageDto.getCreatedTimeEnd());
        }

        wrapper.like(StringUtils.isNotEmpty(bpmProcessInstanceCopyPageDto.getProcessInstanceName()), BpmProcessInstanceCopy::getProcessInstanceName, bpmProcessInstanceCopyPageDto.getProcessInstanceName());
        return wrapper;
    }

    @Override
    public void createProcessInstanceCopy(Collection<Long> userIds, String reason, String taskId) {
        Task task = taskService.getTask(taskId);
        if (ObjectUtil.isNull(task)) {
            throw ServerException.of(BpmResponseStatus.TASK_NOT_EXISTS);
        }
        // 执行抄送
        BpmProcessInstanceCopyServiceImpl currentProxy = (BpmProcessInstanceCopyServiceImpl) AopContext.currentProxy();
        currentProxy.createProcessInstanceCopy(userIds, reason, task.getProcessInstanceId(), task.getTaskDefinitionKey(), task.getId(), task.getName());
    }

    @Transactional
    @Override
    public void createProcessInstanceCopy(Collection<Long> userIds, String reason, String processInstanceId,
                                          String activityId, String activityName, String taskId) {
        // 1.1 校验流程实例存在
        ProcessInstance processInstance = processInstanceService.getProcessInstance(processInstanceId);
        if (processInstance == null) {
            throw ServerException.of(BpmResponseStatus.PROCESS_INSTANCE_NOT_EXISTS);
        }
        // 1.2 校验流程定义存在
        ProcessDefinition processDefinition = processDefinitionService.getProcessDefinition(
                processInstance.getProcessDefinitionId());
        if (processDefinition == null) {
            throw ServerException.of(BpmResponseStatus.PROCESS_DEFINITION_NOT_EXIST);
        }

        // 2. 创建抄送流程
        List<BpmProcessInstanceCopy> copyList = userIds.stream().map(userId -> new BpmProcessInstanceCopy()
                .setUserId(userId).setReason(reason).setStartUserId(Long.valueOf(processInstance.getStartUserId()))
                .setProcessInstanceId(processInstanceId).setProcessInstanceName(processInstance.getName())
                .setCategory(processDefinition.getCategory()).setTaskId(taskId)
                .setActivityId(activityId).setActivityName(activityName)
                .setProcessDefinitionId(processInstance.getProcessDefinitionId())).toList();

        copyList.forEach(copy -> copy.setId(IdGenerator.nextId()));

        baseMapper.insert(copyList);
    }

    @Override
    public void deleteProcessInstanceCopy(String processInstanceId) {
        remove(new LambdaQueryWrapper<BpmProcessInstanceCopy>().eq(BpmProcessInstanceCopy::getProcessInstanceId, processInstanceId));
    }

}
