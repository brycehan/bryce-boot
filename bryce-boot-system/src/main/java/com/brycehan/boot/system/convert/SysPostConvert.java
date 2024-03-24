package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.dto.SysPostDto;
import com.brycehan.boot.system.entity.SysPost;
import com.brycehan.boot.system.vo.SysPostVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统岗位转换器
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysPostConvert {

    public static final SysPostConvert INSTANCE = Mappers.getMapper(SysPostConvert.class);

    public abstract SysPost convert(SysPostDto sysPostDto);

    public abstract SysPostVo convert(SysPost sysPost);

    public abstract List<SysPostVo> convert(List<SysPost> sysPostList);

}