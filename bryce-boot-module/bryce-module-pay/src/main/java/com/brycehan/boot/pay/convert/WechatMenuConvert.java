package com.brycehan.boot.pay.convert;

import com.brycehan.boot.pay.dto.WechatMenuItemDto;
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
public interface WechatMenuConvert {

    WechatMenuConvert INSTANCE = Mappers.getMapper(WechatMenuConvert.class);

    @Mapping(target = "children", source = "subButtons")
    WechatMenuItemDto convert(WxMenuButton wxMenuButton);

    @Mapping(target = "subButtons", source = "children")
    WxMenuButton convert(WechatMenuItemDto wechatMenuItemDto);

    List<WechatMenuItemDto> convert(List<WxMenuButton> wxMenuButtonList);

    List<WxMenuButton> convert2Buttons(List<WechatMenuItemDto> wechatMenuItemDtoList);

}