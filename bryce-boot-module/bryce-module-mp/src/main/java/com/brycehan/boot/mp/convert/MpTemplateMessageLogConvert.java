package com.brycehan.boot.mp.convert;

import com.brycehan.boot.mp.dto.MpTemplateMessageLogDto;
import com.brycehan.boot.mp.entity.MpTemplateMessageLog;
import com.brycehan.boot.mp.vo.MpTemplateMessageLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 微信公众号模版消息发送记录转换器
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Mapper
public interface MpTemplateMessageLogConvert {

    MpTemplateMessageLogConvert INSTANCE = Mappers.getMapper(MpTemplateMessageLogConvert.class);

    MpTemplateMessageLog convert(MpTemplateMessageLogDto mpTemplateMessageLogDto);

    MpTemplateMessageLogVo convert(MpTemplateMessageLog mpTemplateMessageLog);

    List<MpTemplateMessageLogVo> convert(List<MpTemplateMessageLog> mpTemplateMessageLogList);

}