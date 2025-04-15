package com.brycehan.boot.system.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.dto.SysDictDataDto;
import com.brycehan.boot.system.entity.dto.SysDictDataPageDto;
import com.brycehan.boot.system.entity.po.SysDictData;
import com.brycehan.boot.system.entity.vo.SysDictDataSimpleVo;
import com.brycehan.boot.system.entity.vo.SysDictDataVo;

import java.util.List;
import java.util.Map;

/**
 * 系统字典数据服务
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
public interface SysDictDataService extends BaseService<SysDictData> {

    /**
     * 添加系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     */
    void save(SysDictDataDto sysDictDataDto);

    /**
     * 更新系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     */
    void update(SysDictDataDto sysDictDataDto);

    /**
     * 系统字典数据分页查询
     *
     * @param sysDictDataPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysDictDataVo> page(SysDictDataPageDto sysDictDataPageDto);

    /**
     * 获取全部字典列表数据
     *
     * @return 字典列表数据
     */
    Map<String, List<SysDictDataSimpleVo>> dictMap(StatusType statusType);

}
