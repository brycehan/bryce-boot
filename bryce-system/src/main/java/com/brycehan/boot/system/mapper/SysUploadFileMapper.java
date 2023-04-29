package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.dto.SysUploadFilePageDto;
import com.brycehan.boot.system.entity.SysUploadFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 上传文件Mapper接口
 *
 * @author Bryce Han
 * @since 2022/7/15
 */
@Mapper
public interface SysUploadFileMapper extends BryceBaseMapper<SysUploadFile> {

    /**
     * 分页查询
     *
     * @param sysUploadFilePageDto 系统上传文件分页数据传输对象
     * @return 系统上传文件列表
     */
    List<SysUploadFile> page(SysUploadFilePageDto sysUploadFilePageDto);
}
