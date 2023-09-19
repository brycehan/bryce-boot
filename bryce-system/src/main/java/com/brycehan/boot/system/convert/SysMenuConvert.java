package com.brycehan.boot.system.convert;

import com.brycehan.boot.common.base.vo.MenuVo;
import com.brycehan.boot.system.entity.SysMenu;
import com.brycehan.boot.system.dto.SysMenuDto;
import com.brycehan.boot.system.vo.SysMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统菜单转换器
 *
 * @author Bryce Han
 * @since 2023/4/7
 */
@Mapper
public interface SysMenuConvert {

    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenu convert(SysMenuDto sysMenuDto);

    SysMenuVo convert(SysMenu sysMenu);

    List<SysMenuVo> convert(List<SysMenu> sysMenus);

    @Mapping(source = "name", target = "name")
    List<MenuVo> convertMenu(List<SysMenuVo> sysMenuVos);
}
