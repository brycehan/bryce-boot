package com.brycehan.boot.pay.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.pay.entity.WechatApp;

/**
* 微信应用Mapper接口
*
* @author Bryce Han
* @since 2023/11/06
*/
@Mapper
public interface WechatAppMapper extends BryceBaseMapper<WechatApp> {

}
