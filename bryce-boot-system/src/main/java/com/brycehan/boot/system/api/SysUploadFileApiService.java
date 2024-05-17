package com.brycehan.boot.system.api;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.crypto.SecureUtil;
import com.brycehan.boot.api.system.SysUploadFileApi;
import com.brycehan.boot.api.system.vo.SysUploadFileVo;
import com.brycehan.boot.framework.storage.service.StorageService;
import com.brycehan.boot.system.dto.SysAttachmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 存储服务 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUploadFileApiService implements SysUploadFileApi {

    private final StorageService storageService;

    @Override
    public SysUploadFileVo upload(MultipartFile file, String moduleName) {
        // 是否为空
        if(file.isEmpty()) {
            return null;
        }

        SysUploadFileVo sysUploadFileVo = new SysUploadFileVo();
        try {
            // 上传路径
            String path = this.storageService.getPath(file.getOriginalFilename(), moduleName);
            // 上传信息
            // 上传文件
            String url = this.storageService.upload(file.getInputStream(), path);

            SysAttachmentDto attachmentDto = new SysAttachmentDto();
            attachmentDto.setUrl(url);
            attachmentDto.setName(file.getOriginalFilename());
            attachmentDto.setSize(file.getSize());
            attachmentDto.setSuffix(FileNameUtil.getSuffix(file.getOriginalFilename()));
            attachmentDto.setHash(SecureUtil.sha256(file.getInputStream()));
            attachmentDto.setPlatform(this.storageService.storageProperties.getConfig().getType().name());
            attachmentDto.setType(moduleName);

            BeanUtils.copyProperties(attachmentDto, sysUploadFileVo);

        } catch (Exception e) {
            log.error("上传文件失败", e);
            return null;
        }

        return sysUploadFileVo;
    }
}
