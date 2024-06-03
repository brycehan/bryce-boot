package com.brycehan.boot.mp.convert;

import com.brycehan.boot.mp.dto.MpMessageReplyRuleDto;
import com.brycehan.boot.mp.entity.MpMessageReplyRule;
import com.brycehan.boot.mp.vo.MpMessageReplyRuleVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 微信公众号消息回复规则转换器
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MpMessageReplyRuleConvert {

    MpMessageReplyRuleConvert INSTANCE = Mappers.getMapper(MpMessageReplyRuleConvert.class);

    MpMessageReplyRule convert(MpMessageReplyRuleDto mpMessageReplyRuleDto);

    MpMessageReplyRuleVo convert(MpMessageReplyRule mpMessageReplyRule);

    List<MpMessageReplyRuleVo> convert(List<MpMessageReplyRule> mpMessageReplyRuleList);

}