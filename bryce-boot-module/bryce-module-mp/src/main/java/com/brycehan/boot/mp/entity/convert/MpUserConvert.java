package com.brycehan.boot.mp.entity.convert;

import com.brycehan.boot.mp.entity.dto.MpUserDto;
import com.brycehan.boot.mp.entity.po.MpUser;
import com.brycehan.boot.mp.entity.vo.MpUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 微信公众号粉丝转换器
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Mapper
public interface MpUserConvert {

    MpUserConvert INSTANCE = Mappers.getMapper(MpUserConvert.class);

    MpUser convert(MpUserDto mpUserDto);

    MpUserVo convert(MpUser mpUser);

    List<MpUserVo> convert(List<MpUser> mpUserList);

}