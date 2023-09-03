package com.brycehan.boot.system.controller;

import cn.hutool.core.io.FileUtil;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.util.FileUtils;
import com.brycehan.boot.system.dto.SysUploadFilePageDto;
import com.brycehan.boot.system.service.SysUploadFileService;
import com.brycehan.boot.system.vo.SysUploadFileVo;
import com.brycehan.boot.system.entity.SysUploadFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;


/**
 * 上传文件控制器
 *
 * @author Bryce Han
 * @since 2022/7/15
 */
@Tag(name = "sysUploadFile", description = "上传文件API")
@RequestMapping("/system/sysUploadFile")
@RestController
public class SysUploadFileController {

    private final SysUploadFileService sysUploadFileService;

    public SysUploadFileController(SysUploadFileService sysUploadFileService) {
        this.sysUploadFileService = sysUploadFileService;
    }

    /**
     * 保存上传文件
     *
     * @param file 上传文件
     * @return 响应结果
     */
    @Transactional
    @Operation(summary = "保存上传文件")
    @PostMapping
    public ResponseResult<Void> save(@Parameter(description = "上传文件", required = true) MultipartFile file) {
        if (!file.isEmpty()) {
            // 路径
            String fullPath = CommonConstants.UPLOAD_FILE_PATH.concat(LocalDate.now().toString());
            SysUploadFile sysUploadFile = new SysUploadFile();
            sysUploadFile.setId(IdGenerator.nextId());
            sysUploadFile.setOldName(file.getOriginalFilename());
            // 获取文件的后缀名
            sysUploadFile.setSuffix(FileUtils.getSuffix(file.getOriginalFilename()));
            String newName = UUID.randomUUID().toString()
                    .concat(".")
                    .concat(sysUploadFile.getSuffix());
            // 获取文件大小（单位字节）
            sysUploadFile.setSize(file.getSize());
            File targetFile = new File(fullPath, newName);
            if (!targetFile.getParentFile().exists()) {
                //noinspection ResultOfMethodCallIgnored
                targetFile.getParentFile().mkdirs();
            }
            try {
                sysUploadFile.setHash(DigestUtils.md5DigestAsHex(file.getInputStream()));
                // 写入文件
                file.transferTo(targetFile);
            } catch (IOException e) {
                // BusinessException.build()
                throw new RuntimeException("读取文件失败");
            }

            // 获取存储路径
            sysUploadFile.setNewPath(targetFile.getPath());
            // 获取文件的格式
            sysUploadFile.setFileType(FileUtil.getMimeType(targetFile.getPath()));
            this.sysUploadFileService.save(sysUploadFile);
        }

        return ResponseResult.ok();
    }

    /**
     * 删除上传文件
     *
     * @param id 上传文件ID
     * @return 响应结果
     */
    @Operation(summary = "删除上传文件")
    @DeleteMapping(path = "/{id}")
    public ResponseResult<Void> deleteById(@Parameter(description = "上传文件ID", required = true) @PathVariable String id) {
        this.sysUploadFileService.removeById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据上传文件 ID 查询上传文件信息
     *
     * @param id 上传文件ID
     * @return 响应结果
     */
    @Operation(summary = "根据上传文件ID查询上传文件详情")
    @GetMapping(path = "/item/{id}")
    public ResponseResult<SysUploadFile> getById(@Parameter(description = "上传文件ID", required = true) @PathVariable String id) {
        SysUploadFile sysUploadFile = this.sysUploadFileService.getById(id);
        return ResponseResult.ok(sysUploadFile);
    }

    /**
     * 分页查询
     *
     * @param sysUploadFilePageDto 查询条件
     * @return 分页上传文件
     */
    @Operation(summary = "分页查询")
    @GetMapping(path = "/page")
    public ResponseResult<PageResult<SysUploadFileVo>> page(@Parameter(description = "查询信息", required = true) @RequestBody SysUploadFilePageDto sysUploadFilePageDto) {
        PageResult<SysUploadFileVo> page = this.sysUploadFileService.page(sysUploadFilePageDto);
        return ResponseResult.ok(page);
    }

}

