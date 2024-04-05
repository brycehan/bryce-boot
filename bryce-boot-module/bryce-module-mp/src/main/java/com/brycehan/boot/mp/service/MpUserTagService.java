package com.brycehan.boot.mp.service;

import me.chanjar.weixin.mp.bean.tag.WxUserTag;

import java.util.List;

/**
 * 微信公众号粉丝标签服务
 *
 * @author Bryce Han
 * @since 2024/03/31
 */
public interface MpUserTagService {

    /**
     * 创建标签
     *
     * @param name 标签
     */
    void createTag(String name);

    /**
     * 更新标签
     *
     * @param tagId 标签ID
     * @param name 标签名称
     */
    void updateTag(Long tagId, String name);

    /**
     * 删除标签
     *
     * @param tagId 标签ID
     */
    void deleteTag(Long tagId);

    /**
     * 获取微信公众号粉丝标签列表
     *
     * @return 微信公众号粉丝标签列表
     */
    List<WxUserTag> getTags();

    /**
     * 给微信公众号粉丝打标签
     *
     * @param openid 用户openid
     * @param tagId 标签ID
     */
    void tagging(String openid, Long tagId);

    /**
     * 批量给微信公众号粉丝打标签
     *
     * @param openidList 用户openid列表
     * @param tagId 标签ID
     */
    void taggingBatch(String[] openidList, Long tagId);

    /**
     * 取消微信公众号粉丝某个标签
     *
     * @param openid 用户openid
     * @param tagId 标签ID
     */
    void untagging(String openid, Long tagId);

    /**
     * 批量取消微信公众号粉丝某个标签
     *
     * @param openidList 用户openid列表
     * @param tagId 标签ID
     */
    void untaggingBatch(String[] openidList, Long tagId);

}
