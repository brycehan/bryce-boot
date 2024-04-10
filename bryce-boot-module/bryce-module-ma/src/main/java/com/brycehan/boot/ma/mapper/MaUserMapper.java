package com.brycehan.boot.ma.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.ma.entity.MaUser;

/**
* 微信小程序用户Mapper接口
*
* @author Bryce Han
* @since 2024/04/07
*/
@Mapper
public interface MaUserMapper extends BryceBaseMapper<MaUser> {

}
