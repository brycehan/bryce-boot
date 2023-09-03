package com.brycehan.boot.system.convert;

import com.brycehan.boot.system.dto.SysUploadFileDto;
import com.brycehan.boot.system.vo.SysUploadFileVo;
import com.brycehan.boot.system.entity.SysUploadFile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 上传文件表转换器
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Mapper
public interface SysUploadFileConvert {

    SysUploadFileConvert INSTANCE = Mappers.getMapper(SysUploadFileConvert.class);

    SysUploadFile convert(SysUploadFileDto sysUploadFileDto);

    SysUploadFileVo convert(SysUploadFile sysUploadFile);

    List<SysUploadFileVo> convert(List<SysUploadFile> sysUploadFileList);

}