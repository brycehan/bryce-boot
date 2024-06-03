package com.brycehan.boot.mp.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.mp.entity.MpMessageReplyRule;
import org.apache.ibatis.annotations.Mapper;

/**
* 微信公众号消息回复规则Mapper接口
*
* @author Bryce Han
* @since 2024/03/26
*/
@Mapper
public interface MpMessageReplyRuleMapper extends BryceBaseMapper<MpMessageReplyRule> {

}
