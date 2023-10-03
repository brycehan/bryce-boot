package com.brycehan.boot.system.controller;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.crypto.SecureUtil;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.framework.operationlog.annotation.OperateLog;
import com.brycehan.boot.framework.operationlog.annotation.OperateType;
import com.brycehan.boot.framework.storage.service.StorageService;
import com.brycehan.boot.system.vo.SysUploadFileVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 上传文件API
 *
 * @since 2022/7/15
 * @author Bryce Han
 */
@Tag(name = "sysUploadFile", description = "上传文件API")
@RequestMapping("/system/uploadFile")
@RestController
@RequiredArgsConstructor
public class SysUploadFileController {

    private final StorageService storageService;

    /**
     * 保存上传文件
     *
     * @param file 上传文件
     * @return 响应结果
     */
    @Operation(summary = "上传文件")
    @OperateLog(type = OperateType.INSERT)
    @PostMapping
    public ResponseResult<SysUploadFileVo> upload(MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseResult.ok();
        }

        // 上传路径
        String path = this.storageService.getPath(file.getOriginalFilename());

        SysUploadFileVo sysUploadFileVo = new SysUploadFileVo();
        try {

            sysUploadFileVo.setName(file.getOriginalFilename());
            sysUploadFileVo.setSize(file.getSize());
            sysUploadFileVo.setSuffix(FileNameUtil.getSuffix(file.getOriginalFilename()));
            sysUploadFileVo.setHash(SecureUtil.sha256(file.getInputStream()));
            // 上传文件
            String url = this.storageService.upload(file.getInputStream(), path);
            sysUploadFileVo.setUrl(url);

            sysUploadFileVo.setPlatform(this.storageService.storageProperties.getConfig().getType().name());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseResult.ok(sysUploadFileVo);
    }

}

