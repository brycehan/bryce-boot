package com.brycehan.boot.mp.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.mp.entity.MpTemplateMessageLog;
import org.apache.ibatis.annotations.Mapper;

/**
* 微信公众号模版消息发送记录Mapper接口
*
* @author Bryce Han
* @since 2024/03/28
*/
@Mapper
public interface MpTemplateMessageLogMapper extends BryceBaseMapper<MpTemplateMessageLog> {

}
