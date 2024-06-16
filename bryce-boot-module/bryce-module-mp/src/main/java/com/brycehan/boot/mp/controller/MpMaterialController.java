package com.brycehan.boot.mp.controller;

import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.mp.service.MpMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.draft.WxMpDraftArticles;
import me.chanjar.weixin.mp.bean.draft.WxMpUpdateDraft;
import me.chanjar.weixin.mp.bean.material.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 微信公众号素材管理API<br>
 * 官方文档：<a href="https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/New_temporary_materials.html">https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/New_temporary_materials.html</a><br>
 * WxJava开发文档：<a href="https://github.com/Wechat-Group/WxJava/wiki/MP_永久素材管理">https://github.com/Wechat-Group/WxJava/wiki/MP_永久素材管理</a>
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Slf4j
@Tag(name = "微信公众号素材API")
@RequestMapping("/mp/material")
@RestController
@RequiredArgsConstructor
public class MpMaterialController {

    private final MpMaterialService mpMaterialService;

    /**
     * 获取素材总数
     *
     * @return 响应结果
     */
    @Operation(summary = "获取素材总数")
    @GetMapping(path = "/materialCount")
    public ResponseResult<WxMpMaterialCountResult> materialCount() throws WxErrorException {
        WxMpMaterialCountResult result = this.mpMaterialService.materialCount();
        return ResponseResult.ok(result);
    }

    /**
     * 获取素材总数列表
     *
     * @param mediaId 素材ID
     * @return 响应结果
     */
    @Operation(summary = "获取素材总数列表")
    @GetMapping(path = "/materialNewsInfo/{mediaId}")
    public ResponseResult<WxMpMaterialNews> materialNewsInfo(@PathVariable String mediaId) throws WxErrorException {
        WxMpMaterialNews wxMpMaterialNews = this.mpMaterialService.materialNewsInfo(mediaId);
        return ResponseResult.ok(wxMpMaterialNews);
    }

    /**
     * 根据类别获取非图文素材分页列表
     *
     * @param type 素材类型
     * @param page 页码
     * @return 响应结果
     */
    @Operation(summary = "根据类别获取非图文素材分页列表")
    @GetMapping(path = "/materialFileBatchGet")
    public ResponseResult<WxMpMaterialFileBatchGetResult> materialFileBatchGet(@RequestParam(defaultValue = "image") String type,
                                                                           @RequestParam(defaultValue = "1") int page) throws WxErrorException {
        WxMpMaterialFileBatchGetResult fileBatchGetResult = this.mpMaterialService.materialFileBatchGet(type, page);
        return ResponseResult.ok(fileBatchGetResult);
    }

    /**
     * 获取图文素材分页列表
     *
     * @param page 页码
     * @return 响应结果
     */
    @Operation(summary = "获取图文素材分页列表")
    @GetMapping(path = "/materialNewsBatchGet")
    public ResponseResult<WxMpMaterialNewsBatchGetResult> materialNewsBatchGet(@RequestParam(defaultValue = "1") int page) throws WxErrorException {
        WxMpMaterialNewsBatchGetResult materialNewsBatchGetResult = this.mpMaterialService.materialNewsBatchGet(page);
        return ResponseResult.ok(materialNewsBatchGetResult);
    }

    /**
     * 添加图文永久素材
     *
     * @param articles 图文列表
     * @return 响应结果
     */
    @Operation(summary = "添加图文永久素材")
    @PostMapping(path = "/materialNewsUpload")
    public ResponseResult<WxMpMaterialUploadResult> materialNewsUpload(@RequestBody List<WxMpDraftArticles> articles) throws WxErrorException {
        if (CollectionUtils.isEmpty(articles)) {
            return ResponseResult.error("图文列表不能为空");
        }
        WxMpMaterialUploadResult result = this.mpMaterialService.materialNewsUpload(articles);
        return ResponseResult.ok(result);
    }

    /**
     * 修改图文素材文章
     *
     * @param draft 图文素材文章
     * @return 响应结果
     */
    @Operation(summary = "修改图文素材文章")
    @PostMapping(path = "/materialArticleUpdate")
    public ResponseResult<WxMpMaterialNewsBatchGetResult> materialArticleUpdate(@RequestBody WxMpUpdateDraft draft) throws WxErrorException {
        if (draft.getArticles() == null) {
            return ResponseResult.error("文章不能为空");
        }
        this.mpMaterialService.materialArticleUpdate(draft);
        return ResponseResult.ok();
    }

    /**
     * 添加多媒体永久素材
     *
     * @param file 多媒体文件
     * @param fileName 文件名
     * @param mediaType 媒体类型
     * @return 响应结果
     * @throws WxErrorException 微信错误异常
     * @throws IOException io异常
     */
    @Operation(summary = "添加多媒体永久素材")
    @PostMapping(path = "/materialFileUpload")
    public ResponseResult<WxMpMaterialUploadResult> materialFileUpload(MultipartFile file, String fileName, String mediaType) throws WxErrorException, IOException {
        if (file == null) {
            return ResponseResult.error("文件不能为空");
        }

        WxMpMaterialUploadResult result = this.mpMaterialService.materialFileUpload(mediaType, fileName, file);
        return ResponseResult.ok(result);
    }

    /**
     * 删除素材
     *
     * @param mediaId 媒体ID
     * @return 响应结果
     * @throws WxErrorException 微信错误异常
     */
    @Operation(summary = "删除素材")
    @DeleteMapping(path = "/{mediaId}")
    public ResponseResult<Boolean> materialDelete(@PathVariable String mediaId) throws WxErrorException {
        boolean result = this.mpMaterialService.materialDelete(mediaId);
        return ResponseResult.ok(result);
    }

}