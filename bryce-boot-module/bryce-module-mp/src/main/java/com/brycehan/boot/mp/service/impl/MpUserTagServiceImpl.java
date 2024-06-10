package com.brycehan.boot.mp.service.impl;

import com.brycehan.boot.mp.service.MpUserService;
import com.brycehan.boot.mp.service.MpUserTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


/**
 * 微信公众号粉丝服务实现
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "mpUserTagService")
public class MpUserTagServiceImpl implements MpUserTagService {

    private final WxMpService wxMpService;
    private final MpUserService mpUserService;

    public static final String CACHE_KEY = "mp_user_tags";

    /**
     * 创建标签
     *
     * @param name 标签
     */
    @Override
    @CacheEvict(key = CACHE_KEY)
    public void createTag(String name) {
        try {
            this.wxMpService.getUserTagService().tagCreate(name);
        } catch (WxErrorException e) {
            log.error("创建标签失败：{}", e.getMessage());
            throw new RuntimeException("创建标签失败");
        }
    }

    /**
     * 更新标签
     *
     * @param tagId 标签ID
     * @param name 标签名称
     */
    @Override
    @CacheEvict(key = CACHE_KEY)
    public void updateTag(Long tagId, String name) {
        try {
            this.wxMpService.getUserTagService().tagUpdate(tagId, name);
        } catch (WxErrorException e) {
            log.error("更新标签失败：{}", e.getMessage());
            throw new RuntimeException("更新标签失败");
        }
    }

    /**
     * 删除标签
     *
     * @param id 标签ID
     */
    @Override
    @CacheEvict(key = CACHE_KEY)
    public void deleteTag(Long id) {
        try {
            this.wxMpService.getUserTagService().tagDelete(id);
        } catch (WxErrorException e) {
            log.error("删除标签失败：{}", e.getMessage());
            throw new RuntimeException("删除标签失败");
        }
    }

    /**
     * 获取微信公众号粉丝标签列表
     *
     * @return 微信公众号粉丝标签列表
     */
    @Override
    @Cacheable(key = CACHE_KEY)
    public List<WxUserTag> getTags() {
        log.info("拉取公众号粉丝标签");
        try {
            return this.wxMpService.getUserTagService().tagGet();
        } catch (WxErrorException e) {
            log.error("拉取公众号粉丝标签失败：{}", e.getMessage());
            throw new RuntimeException("拉取公众号粉丝标签失败");
        }
    }

    /**
     * 给微信公众号粉丝打标签
     *
     * @param openid 用户openid
     * @param tagId  标签ID
     */
    @Override
    public void tagging(String openid, Long tagId) {
        try {
            this.wxMpService.getUserTagService().batchTagging(tagId, new String[]{ openid });
        } catch (WxErrorException e) {
            log.error("给公众号粉丝打标签失败，openid：{}，tagId：{}，错误消息：{}", openid, tagId, e.getMessage());
            throw new RuntimeException("给公众号粉丝打标签失败");
        }
        this.mpUserService.refreshUserInfo(openid);
    }

    /**
     * 批量给微信公众号粉丝打标签
     *
     * @param openidList 用户openid列表
     * @param tagId      标签ID
     */
    @Override
    public void taggingBatch(String[] openidList, Long tagId) {
        try {
            this.wxMpService.getUserTagService().batchTagging(tagId, openidList);
        } catch (WxErrorException e) {
            log.error("批量给公众号粉丝打标签失败，openid：{}，tagId：{}，错误消息：{}", Arrays.toString(openidList), tagId, e.getMessage());
            throw new RuntimeException("给公众号粉丝打标签失败");
        }

        this.mpUserService.refreshUserInfoAsync(openidList);
    }

    /**
     * 取消微信公众号粉丝某个标签
     *
     * @param openid 用户openid
     * @param tagId  标签ID
     */
    @Override
    public void untagging(String openid, Long tagId) {
        try {
            this.wxMpService.getUserTagService().batchUntagging(tagId, new String[]{ openid });
        } catch (WxErrorException e) {
            log.error("untagging给公众号粉丝取消标签失败，openid：{}，tagId：{}，错误消息：{}", openid, tagId, e.getMessage());
            throw new RuntimeException("给公众号粉丝取消标签失败");
        }
        this.mpUserService.refreshUserInfo(openid);
    }

    /**
     * 批量取消微信公众号粉丝某个标签
     *
     * @param openidList 用户openid列表
     * @param tagId  标签ID
     */
    @Override
    public void untaggingBatch(String[] openidList, Long tagId) {
        try {
            this.wxMpService.getUserTagService().batchUntagging(tagId, openidList);
        } catch (WxErrorException e) {
            log.error("untaggingBatch给公众号粉丝取消标签失败，openid：{}，tagId：{}，错误消息：{}", Arrays.toString(openidList), tagId, e.getMessage());
            throw new RuntimeException("给公众号粉丝取消标签失败");
        }
        this.mpUserService.refreshUserInfoAsync(openidList);
    }
}
