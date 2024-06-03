package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.system.entity.dto.SysRoleDto;
import com.brycehan.boot.system.entity.po.SysRole;
import com.brycehan.boot.system.entity.vo.SysRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统角色转换器
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleConvert {

    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRole convert(SysRoleDto sysRoleDto);

    SysRoleVo convert(SysRole sysRole);

    List<SysRoleVo> convert(List<SysRole> sysRoleList);

}