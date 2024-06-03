package com.brycehan.boot.mp.convert;

import com.brycehan.boot.mp.dto.MpMessageDto;
import com.brycehan.boot.mp.entity.MpMessage;
import com.brycehan.boot.mp.vo.MpMessageVo;
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