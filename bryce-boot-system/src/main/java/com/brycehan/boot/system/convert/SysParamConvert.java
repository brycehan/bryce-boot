package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.dto.SysParamDto;
import com.brycehan.boot.system.entity.SysParam;
import com.brycehan.boot.system.vo.SysParamVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统参数转换器
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysParamConvert {

    SysParamConvert INSTANCE = Mappers.getMapper(SysParamConvert.class);

    SysParam convert(SysParamDto sysParamDto);

    SysParamVo convert(SysParam sysParam);

    List<SysParamVo> convert(List<SysParam> sysParamList);

}