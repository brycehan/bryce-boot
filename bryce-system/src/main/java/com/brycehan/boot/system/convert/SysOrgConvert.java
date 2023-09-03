package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.dto.SysOrgDto;
import com.brycehan.boot.system.entity.SysOrg;
import com.brycehan.boot.system.vo.SysOrgVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 系统机构转换器
 *
 * @author Bryce Han
 * @since 2023/08/31
 */
@Mapper
public interface SysOrgConvert {

    SysOrgConvert INSTANCE = Mappers.getMapper(SysOrgConvert.class);

    SysOrg convert(SysOrgDto sysOrgDto);

    SysOrgVo convert(SysOrg sysOrg);

    List<SysOrgVo> convert(List<SysOrg> sysOrgList);

}