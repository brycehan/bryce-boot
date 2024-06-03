package com.brycehan.boot.mp.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.mp.entity.MpUser;
import org.apache.ibatis.annotations.Mapper;

/**
* 微信公众号粉丝Mapper接口
*
* @author Bryce Han
* @since 2024/03/26
*/
@Mapper
public interface MpUserMapper extends BryceBaseMapper<MpUser> {

}
