package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统登录日志Mapper接口
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Mapper
public interface SysLoginLogMapper extends BryceBaseMapper<SysLoginLog> {

}
