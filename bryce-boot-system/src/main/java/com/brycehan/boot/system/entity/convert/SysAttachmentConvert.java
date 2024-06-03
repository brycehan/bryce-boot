package com.brycehan.boot.system.entity.convert;

import com.brycehan.boot.system.entity.dto.SysAttachmentDto;
import com.brycehan.boot.system.entity.po.SysAttachment;
import com.brycehan.boot.system.entity.vo.SysAttachmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统附件转换器
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysAttachmentConvert {

    SysAttachmentConvert INSTANCE = Mappers.getMapper(SysAttachmentConvert.class);

    SysAttachment convert(SysAttachmentDto sysAttachmentDto);

    SysAttachmentVo convert(SysAttachment sysAttachment);

    List<SysAttachmentVo> convert(List<SysAttachment> sysAttachmentList);

}