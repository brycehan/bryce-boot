package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
* 系统登录日志Mapper接口
*
* @author Bryce Han
* @since 2023/09/25
*/
@Mapper
public interface SysLoginLogMapper extends BryceBaseMapper<SysLoginLog> {

}
