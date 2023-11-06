package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.dto.SysTenantDto;
import com.brycehan.boot.system.entity.SysTenant;
import com.brycehan.boot.system.vo.SysTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统租户转换器
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Mapper
public interface SysTenantConvert {

    SysTenantConvert INSTANCE = Mappers.getMapper(SysTenantConvert.class);

    SysTenant convert(SysTenantDto sysTenantDto);

    SysTenantVo convert(SysTenant sysTenant);

    List<SysTenantVo> convert(List<SysTenant> sysTenantList);

}