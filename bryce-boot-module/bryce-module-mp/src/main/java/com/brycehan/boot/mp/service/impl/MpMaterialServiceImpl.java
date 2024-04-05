package com.brycehan.boot.mp.service.impl;

import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.mp.service.MpMaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.draft.WxMpAddDraft;
import me.chanjar.weixin.mp.bean.draft.WxMpDraftArticles;
import me.chanjar.weixin.mp.bean.draft.WxMpUpdateDraft;
import me.chanjar.weixin.mp.bean.material.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * 微信公众号素材服务实现
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "mp:materialService")
@RequiredArgsConstructor
public class MpMaterialServiceImpl implements MpMaterialService {

    private final WxMpService wxMpService;


    /**
     * 获取素材总数
     *
     * @return 素材总数
     * @throws WxErrorException 微信错误异常
     */
    @Override
    @Cacheable(key = "methodName")
    public WxMpMaterialCountResult materialCount() throws WxErrorException {
        log.info("从API获取素材总量");
        return this.wxMpService.getMaterialService().materialCount();
    }

    /**
     * 获取图文素材详情
     *
     * @param mediaId 媒体ID
     * @return 图文素材详情
     * @throws WxErrorException 微信错误异常
     */
    @Override
    @Cacheable(key = "getMethodName() +'_'+ #mediaId")
    public WxMpMaterialNews materialNewsInfo(String mediaId) throws WxErrorException {
        log.info("从API获取图文素材详情，mediaId：{}", mediaId);
        return this.wxMpService.getMaterialService().materialNewsInfo(mediaId);
    }

    /**
     * 根据类别获取非图文素材分页列表
     *
     * @param type 类型
     * @param page 分页参数
     * @return 非图文素材分页列表
     * @throws WxErrorException 微信错误异常
     */
    @Override
    @Cacheable(key = "getMethodName() + '_' + #type + '_' + #page")
    public WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int page) throws WxErrorException {
        log.info("从API获取媒体素材列表，type：{}，page：{}", type, page);

        final int pageSize = DataConstants.pageSize;
        int offset = (page - 1) * pageSize;

        return this.wxMpService.getMaterialService().materialFileBatchGet(type, offset, pageSize);
    }

    /**
     * 获取图文素材分页列表
     *
     * @param page 分页参数
     * @return 图文素材分页列表
     * @throws WxErrorException 微信错误异常
     */
    @Override
    @Cacheable(key = "getMethodName() + '_' + #page")
    public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int page) throws WxErrorException {
        log.info("从API获取媒体素材列表，page：{}", page);

        final int pageSize = DataConstants.pageSize;
        int offset = (page - 1) * pageSize;

        return this.wxMpService.getMaterialService().materialNewsBatchGet(offset, pageSize);
    }

    /**
     * 添加图文永久素材
     *
     * @param articles 素材
     * @return 操作结果
     * @throws WxErrorException 微信错误异常
     */
    @Override
    @CacheEvict(allEntries = true)
    public WxMpMaterialUploadResult materialNewsUpload(List<WxMpDraftArticles> articles) throws WxErrorException {
        Assert.notEmpty(articles, "图文列表不能为空");
        log.info("上传图文素材");

        WxMpAddDraft draft = new WxMpAddDraft();
        draft.setArticles(articles);
        String draftMediaId = this.wxMpService.getDraftService().addDraft(draft);
        WxMpMaterialUploadResult result = new WxMpMaterialUploadResult();
        result.setMediaId(draftMediaId);
        result.setErrCode(0);

        return result;
    }

    /**
     * 更新图文素材中的某篇文章
     *
     * @param wxMpUpdateDraft 更新内容
     * @throws WxErrorException 微信错误异常
     */
    @Override
    @CacheEvict(allEntries = true)
    public void materialArticleUpdate(WxMpUpdateDraft wxMpUpdateDraft) throws WxErrorException {
        log.info("更新图文素材");
        this.wxMpService.getDraftService().updateDraft(wxMpUpdateDraft);
    }

    /**
     * 添加多媒体永久素材
     *
     * @param mediaType 媒体类型
     * @param fileName  文件名
     * @param file      文件
     * @return 上传结果
     * @throws IOException io异常
     * @throws WxErrorException 微信错误异常
     */
    @Override
    @CacheEvict(allEntries = true)
    public WxMpMaterialUploadResult materialFileUpload(String mediaType, String fileName, MultipartFile file) throws IOException, WxErrorException {
        log.info("上传媒体素材：{}", fileName);
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        File tempFile = File.createTempFile(fileName + "--", originalFilename.substring(originalFilename.lastIndexOf(".")));
        file.transferTo(tempFile);

        WxMpMaterial mpMaterial = new WxMpMaterial();
        mpMaterial.setFile(tempFile);
        mpMaterial.setName(fileName);

        if (WxConsts.MediaFileType.VIDEO.equals(mediaType)) {
            mpMaterial.setVideoTitle(fileName);
        }

        WxMpMaterialUploadResult result = this.wxMpService.getMaterialService().materialFileUpload(mediaType, mpMaterial);
        tempFile.deleteOnExit();

        return result;
    }

    /**
     * 删除素材
     *
     * @param mediaId 媒体ID
     * @return 删除结果，true：删除成功，false：删除失败
     * @throws WxErrorException 微信错误异常
     */
    @Override
    @CacheEvict(allEntries = true)
    public boolean materialDelete(String mediaId) throws WxErrorException {
        log.info("删除素材，mediaId：{}", mediaId);
        return this.wxMpService.getMaterialService().materialDelete(mediaId);
    }
}
