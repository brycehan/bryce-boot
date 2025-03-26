package com.brycehan.boot.bpm.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.bpm.entity.dto.BpmProcessExpressionDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessExpressionPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessExpression;
import com.brycehan.boot.bpm.entity.vo.BpmProcessExpressionVo;

/**
 * 流程表达式服务
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
public interface BpmProcessExpressionService extends BaseService<BpmProcessExpression> {

    /**
     * 添加流程表达式
     *
     * @param bpmProcessExpressionDto 流程表达式Dto
     */
    void save(BpmProcessExpressionDto bpmProcessExpressionDto);

    /**
     * 更新流程表达式
     *
     * @param bpmProcessExpressionDto 流程表达式Dto
     */
    void update(BpmProcessExpressionDto bpmProcessExpressionDto);

    /**
     * 流程表达式分页查询
     *
     * @param bpmProcessExpressionPageDto 查询条件
     * @return 分页信息
     */
    PageResult<BpmProcessExpressionVo> page(BpmProcessExpressionPageDto bpmProcessExpressionPageDto);

    /**
     * 流程表达式导出数据
     *
     * @param bpmProcessExpressionPageDto 流程表达式查询条件
     */
    void export(BpmProcessExpressionPageDto bpmProcessExpressionPageDto);

}
