package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.dto.SysNoticeDto;
import com.brycehan.boot.system.entity.SysNotice;
import com.brycehan.boot.system.vo.SysNoticeVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统通知公告转换器
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysNoticeConvert {

    SysNoticeConvert INSTANCE = Mappers.getMapper(SysNoticeConvert.class);

    SysNotice convert(SysNoticeDto sysNoticeDto);

    SysNoticeVo convert(SysNotice sysNotice);

    List<SysNoticeVo> convert(List<SysNotice> sysNoticeList);

}