package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.system.entity.SysDictData;

/**
* 系统字典数据Mapper接口
*
* @author Bryce Han
* @since 2023/09/08
*/
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

}
