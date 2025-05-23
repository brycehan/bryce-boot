package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.system.entity.dto.SysDictDataDto;
import com.brycehan.boot.system.entity.po.SysDictData;
import com.brycehan.boot.system.entity.vo.SysDictDataSimpleVo;
import com.brycehan.boot.system.entity.vo.SysDictDataVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统字典数据转换器
 *
 * @author Bryce Han
 * @since 2023/09/08
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictDataConvert {

    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    SysDictData convert(SysDictDataDto sysDictDataDto);

    SysDictDataVo convert(SysDictData sysDictData);

    List<SysDictDataVo> convert(List<SysDictData> sysDictDataList);

    SysDictDataSimpleVo convertSimple(SysDictData sysDictData);

    List<SysDictDataSimpleVo> convertSimple(List<SysDictData> sysDictDataList);

}