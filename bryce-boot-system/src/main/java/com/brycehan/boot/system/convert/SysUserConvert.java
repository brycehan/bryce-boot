package com.brycehan.boot.system.convert;

import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.dto.SysUserDto;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.vo.SysUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统用户转换器
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUser convert(SysUserDto sysUserDto);

    SysUserVo convert(SysUser sysUser);

    List<SysUserVo> convert(List<SysUser> sysUserList);

    LoginUser convertLoginUser(SysUser sysUser);

}