package com.brycehan.boot.api.system;

import com.brycehan.boot.api.system.vo.SysUploadFileVo;
import com.brycehan.boot.common.enums.AccessType;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件服务 Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface SysUploadFileApi {

    /**
     * 文件上传
     *
     * @param file       MultipartFile
     * @return http资源地址
     */
    SysUploadFileVo upload(MultipartFile file, AccessType accessType);

    /**
     * 文件上传
     *
     * @param file              MultipartFile 文件
     * @param allowedExtensions 允许上传的文件后缀
     * @return http资源地址
     */
    SysUploadFileVo upload(MultipartFile file, AccessType accessType, String[] allowedExtensions);
}
