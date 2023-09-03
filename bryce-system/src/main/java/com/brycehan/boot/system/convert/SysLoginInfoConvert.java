package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.entity.SysLoginInfo;
import com.brycehan.boot.system.dto.SysLoginInfoDto;
import com.brycehan.boot.system.vo.SysLoginInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 系统登录信息表转换器
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Mapper
public interface SysLoginInfoConvert {

    SysLoginInfoConvert INSTANCE = Mappers.getMapper(SysLoginInfoConvert.class);

    SysLoginInfo convert(SysLoginInfoDto sysLoginInfoDto);

    SysLoginInfoVo convert(SysLoginInfo sysLoginInfo);

    List<SysLoginInfoVo> convert(List<SysLoginInfo> sysLoginInfoList);

}