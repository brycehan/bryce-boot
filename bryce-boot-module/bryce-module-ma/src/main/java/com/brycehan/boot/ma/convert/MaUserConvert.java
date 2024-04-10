package com.brycehan.boot.ma.convert;

import com.brycehan.boot.ma.dto.MaUserDto;
import com.brycehan.boot.ma.entity.MaUser;
import com.brycehan.boot.ma.vo.MaUserLoginVo;
import com.brycehan.boot.ma.vo.MaUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 微信小程序用户转换器
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Mapper
public interface MaUserConvert {

    MaUserConvert INSTANCE = Mappers.getMapper(MaUserConvert.class);

    MaUser convert(MaUserDto maUserDto);

    MaUserVo convert(MaUser maUser);

    List<MaUserVo> convert(List<MaUser> maUserList);

}