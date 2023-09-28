package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.system.entity.SysParam;

/**
* 系统参数Mapper接口
*
* @author Bryce Han
* @since 2023/09/28
*/
@Mapper
public interface SysParamMapper extends BryceBaseMapper<SysParam> {

}
