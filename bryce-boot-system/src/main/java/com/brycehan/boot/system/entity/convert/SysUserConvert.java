package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.system.entity.dto.SysUserAvatarDto;
import com.brycehan.boot.system.entity.dto.SysUserDto;
import com.brycehan.boot.system.entity.dto.SysUserExcelDto;
import com.brycehan.boot.system.entity.dto.SysUserInfoDto;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.entity.vo.SysUserSimpleVo;
import com.brycehan.boot.system.entity.vo.SysUserVo;
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

    SysUser convert(SysUserInfoDto sysUserInfoDto);

    SysUser convert(SysUserAvatarDto sysUserAvatarDto);

    SysUserVo convert(SysUser sysUser);

    SysUserVo convert(LoginUser loginUser);

    List<SysUserVo> convert(List<SysUser> sysUserList);

    List<SysUserSimpleVo> convertSimple(List<SysUser> sysUserList);

    LoginUser convertLoginUser(SysUser sysUser);

    SysUser convert(SysUserExcelDto sysUserExcelDto);

}