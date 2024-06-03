package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.system.entity.dto.SysAreaCodeDto;
import com.brycehan.boot.system.entity.po.SysAreaCode;
import com.brycehan.boot.system.entity.vo.SysAreaCodeVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 地区编码转换器
 *
 * @author Bryce Han
 * @since 2024/04/12
 */
@Mapper
public interface SysAreaCodeConvert {

    SysAreaCodeConvert INSTANCE = Mappers.getMapper(SysAreaCodeConvert.class);

    SysAreaCode convert(SysAreaCodeDto sysAreaCodeDto);

    SysAreaCodeVo convert(SysAreaCode sysAreaCode);

    List<SysAreaCodeVo> convert(List<SysAreaCode> sysAreaCodeList);

}