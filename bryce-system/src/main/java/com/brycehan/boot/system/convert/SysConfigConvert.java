package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.vo.SysConfigVo;
import com.brycehan.boot.system.dto.SysConfigDto;
import com.brycehan.boot.system.entity.SysConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 系统配置表转换器
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Mapper
public interface SysConfigConvert {

    SysConfigConvert INSTANCE = Mappers.getMapper(SysConfigConvert.class);

    SysConfig convert(SysConfigDto sysConfigDto);

    SysConfigVo convert(SysConfig sysConfig);

    List<SysConfigVo> convert(List<SysConfig> sysConfigList);

}