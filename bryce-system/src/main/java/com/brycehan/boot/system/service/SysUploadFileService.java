package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.system.dto.SysUploadFileDto;
import com.brycehan.boot.system.dto.SysUploadFilePageDto;
import com.brycehan.boot.system.entity.SysUploadFile;
import com.brycehan.boot.system.vo.SysUploadFileVo;

/**
 * 上传文件表服务
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
public interface SysUploadFileService extends IService<SysUploadFile> {

    /**
     * 添加上传文件表
     *
     * @param sysUploadFileDto 上传文件表Dto
     */
    void save(SysUploadFileDto sysUploadFileDto);

    /**
     * 更新上传文件表
     *
     * @param sysUploadFileDto 上传文件表Dto
     */
    void update(SysUploadFileDto sysUploadFileDto);

    /**
     * 上传文件表分页查询信息
     *
     * @param sysUploadFilePageDto 上传文件表分页搜索条件
     * @return 分页信息
     */
    PageResult<SysUploadFileVo> page(SysUploadFilePageDto sysUploadFilePageDto);

}
