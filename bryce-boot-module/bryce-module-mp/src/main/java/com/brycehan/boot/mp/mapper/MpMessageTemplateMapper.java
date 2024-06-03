package com.brycehan.boot.mp.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.mp.entity.po.MpMessageTemplate;
import org.apache.ibatis.annotations.Mapper;

/**
* 微信公众号消息模板Mapper接口
*
* @author Bryce Han
* @since 2024/03/28
*/
@Mapper
public interface MpMessageTemplateMapper extends BryceBaseMapper<MpMessageTemplate> {

}
