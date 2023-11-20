package com.brycehan.boot.wechat.mapper;

import com.brycehan.boot.framework.mybatis.mapper.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.wechat.entity.WechatApp;

/**
* 微信应用Mapper接口
*
* @author Bryce Han
* @since 2023/11/06
*/
@Mapper
public interface WechatAppMapper extends BryceBaseMapper<WechatApp> {

}
