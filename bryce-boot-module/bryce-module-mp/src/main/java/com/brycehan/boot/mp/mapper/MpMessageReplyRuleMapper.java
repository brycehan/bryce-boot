package com.brycehan.boot.mp.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.mp.entity.MpMessageReplyRule;

/**
* 微信公众号消息回复规则Mapper接口
*
* @author Bryce Han
* @since 2024/03/26
*/
@Mapper
public interface MpMessageReplyRuleMapper extends BryceBaseMapper<MpMessageReplyRule> {

}
