package com.brycehan.boot.system.convert;

import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.system.dto.SysUserDto;
import com.brycehan.boot.system.vo.SysUserVo;
import com.brycehan.boot.system.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 系统用户转换器
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUser convert(SysUserDto sysUserDto);

    SysUserVo convert(SysUser sysUser);

    List<SysUserVo> convert(List<SysUser> sysUserList);

    LoginUser convertLoginUser(SysUser sysUser);

}