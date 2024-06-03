package com.brycehan.boot.api.system;

import com.brycehan.boot.api.system.vo.SysUploadFileVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param moduleName 模块名
     * @return http资源地址
     */
    @PostMapping(path = "/api/storage/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    SysUploadFileVo upload(@RequestParam MultipartFile file,
                           @RequestParam(defaultValue = "system") String moduleName);

}
