package com.brycehan.boot.mp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.brycehan.boot.mp.entity.po.MpMessage;
import com.brycehan.boot.mp.entity.po.MpMessageReplyRule;
import com.brycehan.boot.mp.service.MpMessageReplyRuleService;
import com.brycehan.boot.mp.service.MpMessageReplyService;
import com.brycehan.boot.mp.service.MpMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 微信公众号消息回则服务实现
 * 官方文档：<a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Service_Center_messages.html#7">客服消息</a><br>
 * 参考 WxJava 客服消息文档：<a href="https://github.com/Wechat-Group/WxJava/wiki/MP_主动发送消息（客服消息）">WxJava 客服消息</a>
 * @author Bryce Han
 * @since 2024/03/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MpMessageReplyServiceImpl implements MpMessageReplyService {

    private final MpMessageReplyRuleService mpMessageReplyRuleService;
    private final ScheduledExecutorService scheduledExecutorService;
    private final WxMpService wxMpService;
    private final MpMessageService mpMessageService;

    /**
     * 根据规则配置，通过微信客服消息接口自动回复消息
     *
     * @param exactMatch 是否精确匹配
     * @param toUser 用户openid
     * @param keywords 匹配关键词
     * @return 是否已自动回复，无匹配规则则不自动回复
     */
    @Override
    public boolean tryAutoReply(boolean exactMatch, String toUser, String keywords) {
        List<MpMessageReplyRule> rules = this.mpMessageReplyRuleService.getMatchedRules(exactMatch, keywords);
        if (rules.isEmpty()) {
            return false;
        }

        try {
            long delay = 0;
            for (MpMessageReplyRule rule : rules) {
                this.scheduledExecutorService.schedule(
                        () -> this.reply(toUser, rule.getReplyType(), rule.getReplyContent()),
                        delay,
                        TimeUnit.SECONDS);
                delay += 1;
            }
            return true;
        } catch (Exception e) {
            log.error("自动回复出错：{}", e.getMessage());
        }

        return false;
    }

    /**
     * 回复文字消息
     *
     * @param toUser       接收消息的用户
     * @param replyContent 回复内容
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyText(String toUser, String replyContent) throws WxErrorException {
        this.wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.TEXT().toUser(toUser).content(replyContent).build());

        JSONObject jsonObject = new JSONObject().fluentPut("content", replyContent);
        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.TEXT, toUser, jsonObject));
    }

    /**
     * 回复图片消息
     *
     * @param toUser  接收消息的用户
     * @param mediaId 图片ID
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyImage(String toUser, String mediaId) throws WxErrorException {
        this.wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.IMAGE().toUser(toUser).mediaId(mediaId).build());

        JSONObject jsonObject = new JSONObject().fluentPut("mediaId", mediaId);
        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.IMAGE, toUser, jsonObject));
    }

    /**
     * 回复录音消息
     *
     * @param toUser  接收消息的用户
     * @param mediaId 录音ID
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyVoice(String toUser, String mediaId) throws WxErrorException {
        this.wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.VOICE().toUser(toUser).mediaId(mediaId).build());

        JSONObject jsonObject = new JSONObject().fluentPut("mediaId", mediaId);
        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.VOICE, toUser, jsonObject));
    }

    /**
     * 回复视频消息
     *
     * @param toUser  接收消息的用户
     * @param mediaId 视频ID
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyVideo(String toUser, String mediaId) throws WxErrorException {
        this.wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.VIDEO().toUser(toUser).mediaId(mediaId).build());

        JSONObject jsonObject = new JSONObject().fluentPut("mediaId", mediaId);
        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.VIDEO, toUser, jsonObject));
    }

    /**
     * 回复音乐消息
     *
     * @param toUser  接收消息的用户
     * @param musicJson 音乐Json
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyMusic(String toUser, String musicJson) throws WxErrorException {
        JSONObject jsonObject = JSONObject.parseObject(musicJson);

        this.wxMpService.getKefuService().sendKefuMessage(
                WxMpKefuMessage.MUSIC().toUser(toUser)
                        .musicUrl(jsonObject.getString("musicurl"))
                        .hqMusicUrl(jsonObject.getString("hqmusicurl"))
                        .title(jsonObject.getString("title"))
                        .description(jsonObject.getString("description"))
                        .thumbMediaId(jsonObject.getString("thumb_media_id"))
                        .build());

        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.MUSIC, toUser, jsonObject));
    }

    /**
     * 回复图文消息（点击跳转到外链）图文消息条数限制在1条以内
     *
     * @param toUser   接收消息的用户
     * @param newsJson 消息内容
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyNews(String toUser, String newsJson) throws WxErrorException {
        WxMpKefuMessage.WxArticle wxArticle = JSONObject.parseObject(newsJson, WxMpKefuMessage.WxArticle.class);
        List<WxMpKefuMessage.WxArticle> newsList = Collections.singletonList(wxArticle);

        this.wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.NEWS().toUser(toUser).articles(newsList).build());

        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.NEWS, toUser, JSONObject.parseObject(newsJson)));
    }

    /**
     * 回复公众号文章消息（点击跳转到图文消息页面）图文消息条数限制在1条以内
     *
     * @param toUser  接收消息的用户
     * @param mediaId 文章ID
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyMpNews(String toUser, String mediaId) throws WxErrorException {
        this.wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(toUser).mediaId(mediaId).build());

        JSONObject jsonObject = new JSONObject().fluentPut("mediaId", mediaId);
        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.MPNEWS, toUser, jsonObject));
    }

    /**
     * 回复卡券消息
     *
     * @param toUser 接收消息的用户
     * @param cardId 卡券ID
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyWechatCard(String toUser, String cardId) throws WxErrorException {
        this.wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.WXCARD().toUser(toUser).cardId(cardId).build());

        JSONObject jsonObject = new JSONObject().fluentPut("cardId", cardId);
        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.WXCARD, toUser, jsonObject));
    }

    /**
     * 回复小程序消息
     *
     * @param toUser 接收消息的用户
     * @param maJson 消息内容
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyMa(String toUser, String maJson) throws WxErrorException {
        JSONObject jsonObject = JSONObject.parseObject(maJson);

        this.wxMpService.getKefuService().sendKefuMessage(
                WxMpKefuMessage.MINIPROGRAMPAGE().
                        toUser(toUser)
                        .title(jsonObject.getString("title"))
                        .appId(jsonObject.getString("appid"))
                        .pagePath(jsonObject.getString("pagepath"))
                        .thumbMediaId(jsonObject.getString("thumb_media_id"))
                        .build());

        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.MINIPROGRAMPAGE, toUser, jsonObject));
    }

    /**
     * 回复菜单消息
     *
     * @param toUser    接收消息的用户
     * @param menusJson 消息内容
     * @throws WxErrorException 微信错误异常
     */
    @Override
    public void replyMenu(String toUser, String menusJson) throws WxErrorException {
        JSONObject jsonObject = JSONObject.parseObject(menusJson);
        List<WxMpKefuMessage.MsgMenu> msgMenus = JSONObject.parseArray(jsonObject.getString("list"), WxMpKefuMessage.MsgMenu.class);

        this.wxMpService.getKefuService().sendKefuMessage(
                WxMpKefuMessage.MSGMENU().
                        toUser(toUser)
                        .headContent(jsonObject.getString("head_content"))
                        .tailContent(jsonObject.getString("tail_content"))
                        .msgMenus(msgMenus)
                        .build());

        this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.MSGMENU, toUser, jsonObject));
    }

}
