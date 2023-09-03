package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brycehan.boot.system.entity.SysOperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统操作日志Mapper接口
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
@Mapper
public interface SysOperateLogMapper extends BaseMapper<SysOperateLog> {

}
