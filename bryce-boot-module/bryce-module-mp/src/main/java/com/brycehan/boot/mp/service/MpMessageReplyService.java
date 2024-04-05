package com.brycehan.boot.mp.service;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信公众号消息回复规则服务
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
public interface MpMessageReplyService {

    Logger log = LoggerFactory.getLogger(MpMessageReplyService.class);

    /**
     * 根据规则配置，通过微信客服消息接口自动回复消息
     *
     * @param exactMatch 是否精确匹配
     * @param toUser 用户openid
     * @param keywords 匹配关键词
     * @return 是否已自动回复，无匹配规则则不自动回复
     */
    boolean tryAutoReply(boolean exactMatch, String toUser, String keywords);

    default void reply(String toUser, String replyType, String replyContent) {
        try {
            switch (replyType) {
                case WxConsts.KefuMsgType.TEXT:
                    this.replyText(toUser, replyContent);
                    break;
                case WxConsts.KefuMsgType.IMAGE:
                    this.replyImage(toUser, replyContent);
                    break;
                case WxConsts.KefuMsgType.VOICE:
                    this.replyVoice(toUser, replyContent);
                    break;
                case WxConsts.KefuMsgType.VIDEO:
                    this.replyVideo(toUser, replyContent);
                    break;
                case WxConsts.KefuMsgType.MUSIC:
                    this.replyMusic(toUser, replyContent);
                    break;
                case WxConsts.KefuMsgType.NEWS:
                    this.replyNews(toUser, replyContent);
                    break;
                case WxConsts.KefuMsgType.MPNEWS:
                    this.replyMpNews(toUser, replyContent);
                    break;
                case WxConsts.KefuMsgType.WXCARD:
                    this.replyWechatCard(toUser, replyContent);
                    break;
                case WxConsts.KefuMsgType.MINIPROGRAMPAGE:
                    this.replyMa(toUser, replyContent);
                    break;
                case WxConsts.KefuMsgType.MSGMENU:
                    this.replyMenu(toUser, replyContent);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("自动回复出错：{}", e.getMessage());
        }
    }

    /**
     * 回复文字消息
     *
     * @param toUser 接收消息的用户
     * @param replyContent 回复内容
     * @throws WxErrorException 微信错误异常
     */
    void replyText(String toUser, String replyContent) throws WxErrorException;

    /**
     * 回复图片消息
     *
     * @param toUser 接收消息的用户
     * @param mediaId 图片ID
     * @throws WxErrorException 微信错误异常
     */
    void replyImage(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复录音消息
     *
     * @param toUser 接收消息的用户
     * @param mediaId 录音ID
     * @throws WxErrorException 微信错误异常
     */
    void replyVoice(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复视频消息
     *
     * @param toUser 接收消息的用户
     * @param mediaId 视频ID
     * @throws WxErrorException 微信错误异常
     */
    void replyVideo(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复音乐消息
     *
     * @param toUser 接收消息的用户
     * @param mediaId 音乐ID
     * @throws WxErrorException 微信错误异常
     */
    void replyMusic(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复图文消息（点击跳转到外链）图文消息条数限制在1条以内
     *
     * @param toUser 接收消息的用户
     * @param newsJson 消息内容
     * @throws WxErrorException 微信错误异常
     */
    void replyNews(String toUser, String newsJson) throws WxErrorException;

    /**
     * 回复公众号文章消息（点击跳转到图文消息页面）图文消息条数限制在1条以内
     *
     * @param toUser 接收消息的用户
     * @param mediaId 文章ID
     * @throws WxErrorException 微信错误异常
     */
    void replyMpNews(String toUser, String mediaId) throws WxErrorException;

    /**
     * 回复卡券消息
     *
     * @param toUser 接收消息的用户
     * @param cardId 卡券ID
     * @throws WxErrorException 微信错误异常
     */
    void replyWechatCard(String toUser, String cardId) throws WxErrorException;

    /**
     * 回复小程序消息
     *
     * @param toUser 接收消息的用户
     * @param maJson 消息内容
     * @throws WxErrorException 微信错误异常
     */
    void replyMa(String toUser, String maJson) throws WxErrorException;

    /**
     * 回复菜单消息
     *
     * @param toUser 接收消息的用户
     * @param menusJson 消息内容
     * @throws WxErrorException 微信错误异常
     */
    void replyMenu(String toUser, String menusJson) throws WxErrorException;

}
