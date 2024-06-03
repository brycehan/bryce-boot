package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.system.entity.dto.SysLoginLogDto;
import com.brycehan.boot.system.entity.po.SysLoginLog;
import com.brycehan.boot.system.entity.vo.SysLoginLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统登录日志转换器
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysLoginLogConvert {

    SysLoginLogConvert INSTANCE = Mappers.getMapper(SysLoginLogConvert.class);

    SysLoginLog convert(SysLoginLogDto sysLoginLogDto);

    SysLoginLogVo convert(SysLoginLog sysLoginLog);

    List<SysLoginLogVo> convert(List<SysLoginLog> sysLoginLogList);

}