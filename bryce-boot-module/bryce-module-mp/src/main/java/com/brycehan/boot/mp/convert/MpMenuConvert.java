package com.brycehan.boot.mp.convert;

import com.brycehan.boot.mp.dto.MpMenuItemDto;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 微信菜单转换器
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MpMenuConvert {

    MpMenuConvert INSTANCE = Mappers.getMapper(MpMenuConvert.class);

    @Mapping(target = "children", source = "subButtons")
    MpMenuItemDto convert(WxMenuButton wxMenuButton);

    @Mapping(target = "subButtons", source = "children")
    WxMenuButton convert(MpMenuItemDto mpMenuItemDto);

    List<MpMenuItemDto> convert(List<WxMenuButton> wxMenuButtonList);

    List<WxMenuButton> convert2Buttons(List<MpMenuItemDto> mpMenuItemDtoList);

}