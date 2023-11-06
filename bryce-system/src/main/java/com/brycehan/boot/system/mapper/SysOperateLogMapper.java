package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysOperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统操作日志Mapper接口
 *
 * @since 2023/09/27
 * @author Bryce Han
 */
@Mapper
public interface SysOperateLogMapper extends BryceBaseMapper<SysOperateLog> {

}
