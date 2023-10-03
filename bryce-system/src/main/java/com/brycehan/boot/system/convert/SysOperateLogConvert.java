package com.brycehan.boot.system.convert;

import com.brycehan.boot.framework.operationlog.OperateLogDto;
import com.brycehan.boot.system.dto.SysOperateLogDto;
import com.brycehan.boot.system.entity.SysOperateLog;
import com.brycehan.boot.system.vo.SysOperateLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统操作日志转换器
 *
 * @since 2023/4/7
 * @author Bryce Han
 */
@Mapper
public interface SysOperateLogConvert {

    SysOperateLogConvert INSTANCE = Mappers.getMapper(SysOperateLogConvert.class);

    SysOperateLog convert(SysOperateLogDto sysOperateLogDto);

    SysOperateLogVo convert(SysOperateLog sysOperateLog);

    SysOperateLog convert(OperateLogDto operateLogDto);

    List<SysOperateLogVo> convert(List<SysOperateLog> sysOperateLogList);

}
