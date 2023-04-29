package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.system.dto.SysUploadFilePageDto;
import com.brycehan.boot.system.entity.SysUploadFile;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

/**
 * 上传文件服务类
 *
 * @author Bryce Han
 * @since 2022/7/15
 */
@Validated
public interface SysUploadFileService extends IService<SysUploadFile> {

    /**
     * 分页查询信息结果
     *
     * @param sysUploadFilePageDto 搜索条件
     * @return 分页信息
     */
    PageInfo<SysUploadFile> page(@NotNull SysUploadFilePageDto sysUploadFilePageDto);
}
