package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.api.system.vo.BpmPostVo;
import com.brycehan.boot.system.entity.dto.SysPostDto;
import com.brycehan.boot.system.entity.po.SysPost;
import com.brycehan.boot.system.entity.vo.SysPostVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    SysPostConvert INSTANCE = Mappers.getMapper(SysPostConvert.class);

    SysPost convert(SysPostDto sysPostDto);

    SysPostVo convert(SysPost sysPost);

    List<SysPostVo> convert(List<SysPost> sysPostList);

    @Mapping(target = "status", source = "status.value")
    BpmPostVo convertBpm(SysPost sysPost);

    List<BpmPostVo> convertBpm(List<SysPost> sysPostList);

}