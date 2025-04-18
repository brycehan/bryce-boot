package com.brycehan.boot.system.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.dto.SysDictTypeCodeDto;
import com.brycehan.boot.system.entity.dto.SysDictTypeDto;
import com.brycehan.boot.system.entity.dto.SysDictTypePageDto;
import com.brycehan.boot.system.entity.po.SysDictType;
import com.brycehan.boot.system.entity.vo.SysDictTypeSimpleVo;
import com.brycehan.boot.system.entity.vo.SysDictTypeVo;

import java.util.List;

/**
 * 系统字典类型服务
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
public interface SysDictTypeService extends BaseService<SysDictType> {

    /**
     * 添加系统字典类型
     *
     * @param sysDictTypeDto 系统字典类型Dto
     */
    void save(SysDictTypeDto sysDictTypeDto);

    /**
     * 更新系统字典类型
     *
     * @param sysDictTypeDto 系统字典类型Dto
     */
    void update(SysDictTypeDto sysDictTypeDto);

    /**
     * 系统字典类型分页查询
     *
     * @param sysDictTypePageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysDictTypeVo> page(SysDictTypePageDto sysDictTypePageDto);

    /**
     * 系统字典类型导出数据
     *
     * @param sysDictTypePageDto 系统字典类型查询条件
     */
    void export(SysDictTypePageDto sysDictTypePageDto);

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型精简信息列表
     */
    List<SysDictTypeSimpleVo> getSimpleList();

    /**
     * 校验字典类型编码是否唯一
     *
     * @param sysDictTypeCodeDto 字典类型编码Dto
     * @return true：唯一，false：不唯一
     */
    boolean checkDictTypeCodeUnique(SysDictTypeCodeDto sysDictTypeCodeDto);

}
