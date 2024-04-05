package com.brycehan.boot.mp.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.mp.entity.MpQrCode;

/**
* 微信公众号带参二维码Mapper接口
*
* @author Bryce Han
* @since 2024/03/28
*/
@Mapper
public interface MpQrCodeMapper extends BryceBaseMapper<MpQrCode> {

}
