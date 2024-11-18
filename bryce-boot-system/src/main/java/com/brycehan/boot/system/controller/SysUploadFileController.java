package com.brycehan.boot.system.controller;

import com.brycehan.boot.api.system.SysUploadFileApi;
import com.brycehan.boot.api.system.vo.SysUploadFileVo;
import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 上传文件API
 *
 * @since 2022/7/15
 * @author Bryce Han
 */
@Tag(name = "上传文件")
@RequestMapping("/storage/uploadFile")
@RestController
@RequiredArgsConstructor
public class SysUploadFileController {

    private final SysUploadFileApi sysUploadFileApi;

    /**
     * 保存上传文件
     *
     * @param file 上传文件
     * @return 响应结果
     */
    @Operation(summary = "上传文件")
    @OperateLog(type = OperatedType.INSERT)
    @PostMapping(path = "/list")
    public ResponseResult<List<SysUploadFileVo>> uploadList(@RequestParam List<MultipartFile> file,
                                                        @RequestParam(defaultValue = "system") String moduleName) {

        if (CollectionUtils.isEmpty(file)) {
            return ResponseResult.error("上传文件不能为空");
        }

        List<SysUploadFileVo> uploadFileVoList = new CopyOnWriteArrayList<>();

        file.parallelStream().forEach(fileItem -> {
                SysUploadFileVo sysUploadFileVo = this.sysUploadFileApi.upload(fileItem, moduleName);
                if (sysUploadFileVo != null) {
                    uploadFileVoList.add(sysUploadFileVo);
                }
        });

        return ResponseResult.ok(uploadFileVoList);
    }

    /**
     * 保存上传文件
     *
     * @param file 上传文件
     * @return 响应结果
     */
    @Operation(summary = "上传文件")
    @OperateLog(type = OperatedType.INSERT)
    @PostMapping
    public ResponseResult<SysUploadFileVo> upload(@RequestParam MultipartFile file,
                                                        @RequestParam(defaultValue = "system") String moduleName) {
        SysUploadFileVo sysUploadFileVo = this.sysUploadFileApi.upload(file, moduleName);
        return ResponseResult.ok(sysUploadFileVo);
    }

}

