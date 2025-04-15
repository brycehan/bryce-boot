package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.system.entity.dto.SysDictTypeDto;
import com.brycehan.boot.system.entity.po.SysDictType;
import com.brycehan.boot.system.entity.vo.SysDictTypeSimpleVo;
import com.brycehan.boot.system.entity.vo.SysDictTypeVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统字典类型转换器
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictTypeConvert {

    SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

    SysDictType convert(SysDictTypeDto sysDictTypeDto);

    SysDictTypeVo convert(SysDictType sysDictType);

    List<SysDictTypeVo> convert(List<SysDictType> sysDictTypeList);

    SysDictTypeSimpleVo convertSimple(SysDictType sysDictType);

    List<SysDictTypeSimpleVo> convertSimple(List<SysDictType> sysDictTypeList);
}