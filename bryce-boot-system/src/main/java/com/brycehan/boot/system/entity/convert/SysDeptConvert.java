package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.system.entity.dto.SysDeptDto;
import com.brycehan.boot.system.entity.po.SysDept;
import com.brycehan.boot.system.entity.vo.SysDeptVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统部门转换器
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDeptConvert {

    SysDeptConvert INSTANCE = Mappers.getMapper(SysDeptConvert.class);

    SysDept convert(SysDeptDto sysDeptDto);

    SysDeptVo convert(SysDept sysDept);

    List<SysDeptVo> convert(List<SysDept> sysDeptList);

}