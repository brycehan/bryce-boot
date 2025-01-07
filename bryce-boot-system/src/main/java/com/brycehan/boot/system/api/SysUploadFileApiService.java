package com.brycehan.boot.system.api;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.crypto.SecureUtil;
import com.brycehan.boot.api.system.SysUploadFileApi;
import com.brycehan.boot.api.system.vo.SysUploadFileVo;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.FileUploadUtils;
import com.brycehan.boot.common.util.MimeTypeUtils;
import com.brycehan.boot.framework.storage.service.StorageService;
import com.brycehan.boot.system.entity.dto.SysAttachmentDto;
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
    public SysUploadFileVo upload(MultipartFile file, AccessType accessType) {
        return this.upload(file, accessType, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
    }

    @Override
    public SysUploadFileVo upload(MultipartFile file, AccessType accessType, String[] allowedExtensions) {
        // 是否为空
        if(file.isEmpty()) {
            return null;
        }

        // 文件格式校验
        FileUploadUtils.assertAllowed(file, allowedExtensions);

        SysUploadFileVo sysUploadFileVo = new SysUploadFileVo();
        try {
            // 上传路径
            String path = this.storageService.getPath(file.getOriginalFilename());
            // 上传文件
            String url = this.storageService.upload(file.getInputStream(), path, accessType);

            // 上传信息
            SysAttachmentDto attachmentDto = new SysAttachmentDto();
            attachmentDto.setName(file.getOriginalFilename());
            attachmentDto.setUrl(url);
            attachmentDto.setSuffix(FileNameUtil.getSuffix(file.getOriginalFilename()));
            attachmentDto.setSize(file.getSize());
            attachmentDto.setAccessType(accessType);
            attachmentDto.setHash(SecureUtil.sha256(file.getInputStream()));
            attachmentDto.setPlatform(this.storageService.storageProperties.getConfig().getType().name());

            BeanUtils.copyProperties(attachmentDto, sysUploadFileVo);

        } catch (Exception e) {
            log.error("上传文件失败", e);
            return null;
        }

        return sysUploadFileVo;
    }
}
