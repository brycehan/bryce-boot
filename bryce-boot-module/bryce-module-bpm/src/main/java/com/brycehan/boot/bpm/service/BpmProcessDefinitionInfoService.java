package com.brycehan.boot.bpm.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionInfoVo;

import java.util.List;
import java.util.Map;

/**
 * 流程定义信息服务
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
public interface BpmProcessDefinitionInfoService extends BaseService<BpmProcessDefinitionInfo> {

    /**
     * 流程定义信息分页查询
     *
     * @param bpmProcessDefinitionInfoPageDto 查询条件
     * @return 分页信息
     */
    PageResult<BpmProcessDefinitionInfoVo> page(BpmProcessDefinitionInfoPageDto bpmProcessDefinitionInfoPageDto);

    /**
     * 流程定义信息导出数据
     *
     * @param bpmProcessDefinitionInfoPageDto 流程定义信息查询条件
     */
    void export(BpmProcessDefinitionInfoPageDto bpmProcessDefinitionInfoPageDto);

    /**
     * 获取流程定义信息Map
     *
     * @param processDefinitionIds 流程定义编号列表
     * @return 流程定义信息
     */
    Map<String, BpmProcessDefinitionInfo> getProcessDefinitionInfoMap(List<String> processDefinitionIds);

    /**
     * 获取流程定义信息
     *
     * @param processDefinitionId 流程定义信息ID
     * @return 流程定义信息
     */
    BpmProcessDefinitionInfo getProcessDefinitionInfo(String processDefinitionId);
}
