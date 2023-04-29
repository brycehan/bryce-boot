package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.dto.SysPostDto;
import com.brycehan.boot.system.entity.SysPost;
import com.brycehan.boot.system.vo.SysPostVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统岗位转换器
 *
 * @author Bryce Han
 * @since 2023/4/7
 */
@Mapper
public interface SysPostConvert {

    SysPostConvert INSTANCE = Mappers.getMapper(SysPostConvert.class);

    SysPost convert(SysPostDto sysPostDto);

    SysPostVo convert(SysPost sysPost);

    List<SysPostVo> convert(List<SysPost> sysPostList);

}
