package com.brycehan.boot.mp.entity.convert;

import com.brycehan.boot.mp.entity.dto.MpMessageDto;
import com.brycehan.boot.mp.entity.po.MpMessage;
import com.brycehan.boot.mp.entity.vo.MpMessageVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 微信公众号消息转换器
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Mapper
public interface MpMessageConvert {

    MpMessageConvert INSTANCE = Mappers.getMapper(MpMessageConvert.class);

    MpMessage convert(MpMessageDto mpMessageDto);

    MpMessageVo convert(MpMessage mpMessage);

    List<MpMessageVo> convert(List<MpMessage> mpMessageList);

}