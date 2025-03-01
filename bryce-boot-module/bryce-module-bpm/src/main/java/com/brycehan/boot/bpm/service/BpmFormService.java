package com.brycehan.boot.bpm.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.bpm.entity.dto.BpmFormDto;
import com.brycehan.boot.bpm.entity.dto.BpmFormPageDto;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.entity.vo.BpmFormVo;

import java.util.List;

/**
 * 表单定义服务
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
public interface BpmFormService extends BaseService<BpmForm> {

    /**
     * 添加表单定义
     *
     * @param bpmFormDto 表单定义Dto
     */
    void save(BpmFormDto bpmFormDto);

    /**
     * 更新表单定义
     *
     * @param bpmFormDto 表单定义Dto
     */
    void update(BpmFormDto bpmFormDto);

    /**
     * 表单定义分页查询
     *
     * @param bpmFormPageDto 查询条件
     * @return 分页信息
     */
    PageResult<BpmFormVo> page(BpmFormPageDto bpmFormPageDto);

    /**
     * 表单定义列表查询
     *
     * @param bpmFormPageDto 查询条件
     * @return 表单定义列表
     */
    List<BpmFormVo> list(BpmFormPageDto bpmFormPageDto);

    /**
     * 表单定义导出数据
     *
     * @param bpmFormPageDto 表单定义查询条件
     */
    void export(BpmFormPageDto bpmFormPageDto);

}
