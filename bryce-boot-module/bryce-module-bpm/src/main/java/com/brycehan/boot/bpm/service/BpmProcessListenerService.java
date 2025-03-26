package com.brycehan.boot.bpm.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.bpm.entity.dto.BpmProcessListenerDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessListenerPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessListener;
import com.brycehan.boot.bpm.entity.vo.BpmProcessListenerVo;

/**
 * 流程监听器服务
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
public interface BpmProcessListenerService extends BaseService<BpmProcessListener> {

    /**
     * 添加流程监听器
     *
     * @param bpmProcessListenerDto 流程监听器Dto
     */
    void save(BpmProcessListenerDto bpmProcessListenerDto);

    /**
     * 更新流程监听器
     *
     * @param bpmProcessListenerDto 流程监听器Dto
     */
    void update(BpmProcessListenerDto bpmProcessListenerDto);

    /**
     * 流程监听器分页查询
     *
     * @param bpmProcessListenerPageDto 查询条件
     * @return 分页信息
     */
    PageResult<BpmProcessListenerVo> page(BpmProcessListenerPageDto bpmProcessListenerPageDto);

    /**
     * 流程监听器导出数据
     *
     * @param bpmProcessListenerPageDto 流程监听器查询条件
     */
    void export(BpmProcessListenerPageDto bpmProcessListenerPageDto);

}
