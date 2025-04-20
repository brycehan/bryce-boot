package com.brycehan.boot.bpm.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.bpm.entity.dto.BpmCategoryDto;
import com.brycehan.boot.bpm.entity.dto.BpmCategoryPageDto;
import com.brycehan.boot.bpm.entity.po.BpmCategory;
import com.brycehan.boot.bpm.entity.vo.BpmCategoryVo;

import java.util.List;
import java.util.Map;

/**
 * 流程分类服务
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
public interface BpmCategoryService extends BaseService<BpmCategory> {

    /**
     * 添加流程分类
     *
     * @param bpmCategoryDto 流程分类Dto
     */
    void save(BpmCategoryDto bpmCategoryDto);

    /**
     * 更新流程分类
     *
     * @param bpmCategoryDto 流程分类Dto
     */
    void update(BpmCategoryDto bpmCategoryDto);

    /**
     * 流程分类分页查询
     *
     * @param bpmCategoryPageDto 查询条件
     * @return 分页信息
     */
    PageResult<BpmCategoryVo> page(BpmCategoryPageDto bpmCategoryPageDto);

    /**
     * 流程分类列表查询
     *
     * @param bpmCategoryPageDto 查询条件
     * @return 流程分类列表
     */
    List<BpmCategoryVo> list(BpmCategoryPageDto bpmCategoryPageDto);

    /**
     * 流程分类导出数据
     *
     * @param bpmCategoryPageDto 流程分类查询条件
     */
    void export(BpmCategoryPageDto bpmCategoryPageDto);

    /**
     * 根据流程分类ID获取流程分类名称
     *
     * @param categoryId 流程分类ID
     * @return 流程分类名称
     */
    String getCategoryName(Long categoryId);

    /**
     * 获取流程分类名称Map
     *
     * @param categoryIds 流程分类ID集合
     * @return 流程分类名称Map
     */
    Map<Long, String> getCategoryNameMap(List<Long> categoryIds);
}
