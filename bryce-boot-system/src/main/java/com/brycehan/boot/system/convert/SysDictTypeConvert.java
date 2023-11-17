package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.dto.SysDictTypeDto;
import com.brycehan.boot.system.entity.SysDictType;
import com.brycehan.boot.system.vo.SysDictTypeVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统字典类型转换器
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Mapper
public interface SysDictTypeConvert {

    SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

    SysDictType convert(SysDictTypeDto sysDictTypeDto);

    SysDictTypeVo convert(SysDictType sysDictType);

    List<SysDictTypeVo> convert(List<SysDictType> sysDictTypeList);

}