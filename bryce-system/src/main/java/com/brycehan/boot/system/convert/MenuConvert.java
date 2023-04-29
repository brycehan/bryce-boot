package com.brycehan.boot.system.convert;

import com.brycehan.boot.common.base.vo.MenuVo;
import com.brycehan.boot.system.vo.SysMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜单转换器
 *
 * @author Bryce Han
 * @since 2023/4/7
 */
@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    @Mappings({
            @Mapping(source = "menuName", target = "name"),
            @Mapping(source = "children", target = "routes"),
    })
    MenuVo convert(SysMenuVo sysMenuVo);

    List<MenuVo> convert(List<SysMenuVo> sysMenuVos);
}
