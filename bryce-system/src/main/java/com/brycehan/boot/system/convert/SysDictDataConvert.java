package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.dto.SysDictDataDto;
import com.brycehan.boot.system.entity.SysDictData;
import com.brycehan.boot.system.vo.SysDictDataVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统字典数据转换器
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Mapper
public interface SysDictDataConvert {

    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    SysDictData convert(SysDictDataDto sysDictDataDto);

    SysDictDataVo convert(SysDictData sysDictData);

    List<SysDictDataVo> convert(List<SysDictData> sysDictDataList);

}