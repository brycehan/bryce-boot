package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.system.entity.SysOperateLog;

/**
* 系统操作日志Mapper接口
*
* @author Bryce Han
* @since 2023/09/27
*/
@Mapper
public interface SysOperateLogMapper extends BryceBaseMapper<SysOperateLog> {

}
