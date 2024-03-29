package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.convert.SysDictTypeConvert;
import com.brycehan.boot.system.dto.SysDictTypeDto;
import com.brycehan.boot.system.dto.SysDictTypePageDto;
import com.brycehan.boot.system.entity.SysDictType;
import com.brycehan.boot.system.vo.SysDictTypeVo;
import com.brycehan.boot.system.vo.SysDictVo;

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
    default void save(SysDictTypeDto sysDictTypeDto) {
        SysDictType sysDictType = SysDictTypeConvert.INSTANCE.convert(sysDictTypeDto);
        sysDictType.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysDictType);
    }

    /**
     * 更新系统字典类型
     *
     * @param sysDictTypeDto 系统字典类型Dto
     */
    default void update(SysDictTypeDto sysDictTypeDto) {
        SysDictType sysDictType = SysDictTypeConvert.INSTANCE.convert(sysDictTypeDto);
        this.getBaseMapper().updateById(sysDictType);
    }

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
     * 获取全部字典列表数据
     *
     * @return 字典列表数据
     */
    List<SysDictVo> dictList();

}
