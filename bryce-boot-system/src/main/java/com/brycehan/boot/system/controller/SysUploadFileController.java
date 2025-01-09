package com.brycehan.boot.system.controller;

import com.brycehan.boot.api.system.StorageApi;
import com.brycehan.boot.api.system.vo.StorageVo;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.NotEmptyElements;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 上传文件API
 *
 * @author Bryce Han
 * @since 2022/7/15
 */
@Tag(name = "上传文件")
@RequestMapping("/storage/uploadFile")
@RestController
@Validated
@RequiredArgsConstructor
public class SysUploadFileController {

    private final StorageApi storageApi;

    /**
     * 上传单个文件
     *
     * @param file 上传文件
     * @return 响应结果
     */
    @Operation(summary = "上传单个文件")
    @OperateLog(type = OperatedType.INSERT)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<StorageVo> upload(@NotNull MultipartFile file, @NotNull AccessType accessType) {
        StorageVo storageVo = this.storageApi.upload(file, accessType);
        return ResponseResult.ok(storageVo);
    }

    /**
     * 上传多个文件
     *
     * @param file 上传文件
     * @return 响应结果
     */
    @Operation(summary = "上传多个文件")
    @OperateLog(type = OperatedType.INSERT)
    @PostMapping(path = "/list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<List<StorageVo>> upload(@NotNull @NotEmptyElements @Size(min = 1, max = 9) List<MultipartFile> file, @NotNull AccessType accessType) {
        List<StorageVo> storageVoList = new CopyOnWriteArrayList<>();

        file.parallelStream().filter(f -> !f.isEmpty()).forEach(fileItem -> {
            StorageVo storageVo = this.storageApi.upload(fileItem, accessType);
            if (storageVo != null) {
                storageVoList.add(storageVo);
            }
        });

        return ResponseResult.ok(storageVoList);
    }
}

