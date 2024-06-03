package com.brycehan.boot.ma.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.ma.entity.MaUser;
import org.apache.ibatis.annotations.Mapper;

/**
* 微信小程序用户Mapper接口
*
* @author Bryce Han
* @since 2024/04/07
*/
@Mapper
public interface MaUserMapper extends BryceBaseMapper<MaUser> {

}
