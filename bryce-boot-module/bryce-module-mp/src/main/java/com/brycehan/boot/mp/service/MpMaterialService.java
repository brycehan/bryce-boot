package com.brycehan.boot.mp.service;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.draft.WxMpDraftArticles;
import me.chanjar.weixin.mp.bean.draft.WxMpUpdateDraft;
import me.chanjar.weixin.mp.bean.material.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 微信公众号素材服务
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
public interface MpMaterialService {

    /**
     * 获取素材总数
     * @return 素材总数
     * @throws WxErrorException 微信错误异常
     */
    WxMpMaterialCountResult materialCount() throws WxErrorException;

    /**
     * 获取图文素材详情
     *
     * @param mediaId 媒体ID
     * @return 图文素材详情
     * @throws WxErrorException 微信错误异常
     */
    WxMpMaterialNews materialNewsInfo(String mediaId) throws WxErrorException;

    /**
     * 根据类别获取非图文素材分页列表
     *
     * @param type 类型
     * @param page 分页页码
     * @return 非图文素材分页列表
     * @throws WxErrorException 微信错误异常
     */
    WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int page) throws WxErrorException;

    /**
     * 获取图文素材分页列表
     *
     * @param page 分页参数
     * @return 图文素材分页列表
     * @throws WxErrorException 微信错误异常
     */
    WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int page) throws WxErrorException;

    /**
     * 添加图文永久素材
     * @param articles 素材
     * @return 操作结果
     * @throws WxErrorException 微信错误异常
     */
    WxMpMaterialUploadResult materialNewsUpload(List<WxMpDraftArticles> articles) throws WxErrorException;

    /**
     * 更新图文素材中的某篇文章
     *
     * @param wxMpUpdateDraft 更新内容
     * @throws WxErrorException 微信错误异常
     */
    void materialArticleUpdate(WxMpUpdateDraft wxMpUpdateDraft) throws WxErrorException;

    /**
     * 添加多媒体永久素材
     *
     * @param mediaType 媒体类型
     * @param fileName 文件名
     * @param file 文件
     * @return 上传结果
     * @throws IOException io异常
     * @throws WxErrorException 微信错误异常
     */
    WxMpMaterialUploadResult materialFileUpload(String mediaType, String fileName, MultipartFile file) throws IOException, WxErrorException;

    /**
     * 删除素材
     *
     * @param mediaId 媒体ID
     * @return 删除结果，true：删除成功，false：删除失败
     * @throws WxErrorException 微信错误异常
     */
    boolean materialDelete(String mediaId) throws WxErrorException;

}
