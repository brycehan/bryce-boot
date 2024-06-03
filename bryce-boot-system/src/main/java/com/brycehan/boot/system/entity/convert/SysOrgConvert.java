package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.system.entity.dto.SysOrgDto;
import com.brycehan.boot.system.entity.po.SysOrg;
import com.brycehan.boot.system.entity.vo.SysOrgVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统机构转换器
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysOrgConvert {

    SysOrgConvert INSTANCE = Mappers.getMapper(SysOrgConvert.class);

    SysOrg convert(SysOrgDto sysOrgDto);

    SysOrgVo convert(SysOrg sysOrg);

    List<SysOrgVo> convert(List<SysOrg> sysOrgList);

}