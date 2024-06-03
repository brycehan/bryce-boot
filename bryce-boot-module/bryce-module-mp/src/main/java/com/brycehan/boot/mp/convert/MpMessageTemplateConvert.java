package com.brycehan.boot.mp.convert;

import com.brycehan.boot.mp.dto.MpMessageTemplateDto;
import com.brycehan.boot.mp.entity.MpMessageTemplate;
import com.brycehan.boot.mp.vo.MpMessageTemplateVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 微信公众号消息模板转换器
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Mapper
public interface MpMessageTemplateConvert {

    MpMessageTemplateConvert INSTANCE = Mappers.getMapper(MpMessageTemplateConvert.class);

    MpMessageTemplate convert(MpMessageTemplateDto mpMessageTemplateDto);

    MpMessageTemplateVo convert(MpMessageTemplate mpMessageTemplate);

    List<MpMessageTemplateVo> convert(List<MpMessageTemplate> mpMessageTemplateList);

}