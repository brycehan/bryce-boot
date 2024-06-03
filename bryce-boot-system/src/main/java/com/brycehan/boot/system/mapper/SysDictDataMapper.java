package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brycehan.boot.system.entity.po.SysDictData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统字典数据Mapper接口
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

}
