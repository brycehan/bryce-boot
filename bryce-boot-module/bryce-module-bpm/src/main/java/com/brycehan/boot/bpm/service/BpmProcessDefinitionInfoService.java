package com.brycehan.boot.bpm.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionInfoVo;

/**
 * 流程定义信息服务
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
public interface BpmProcessDefinitionInfoService extends BaseService<BpmProcessDefinitionInfo> {

    /**
     * 添加流程定义信息
     *
     * @param bpmProcessDefinitionInfoDto 流程定义信息Dto
     */
    void save(BpmProcessDefinitionInfoDto bpmProcessDefinitionInfoDto);

    /**
     * 更新流程定义信息
     *
     * @param bpmProcessDefinitionInfoDto 流程定义信息Dto
     */
    void update(BpmProcessDefinitionInfoDto bpmProcessDefinitionInfoDto);

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

}
