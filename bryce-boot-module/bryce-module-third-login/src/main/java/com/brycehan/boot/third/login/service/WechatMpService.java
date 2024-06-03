package com.brycehan.boot.third.login.service;

import com.brycehan.boot.mp.entity.po.MpUser;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信公众号服务
 *
 * @author Bryce Han
 * @since 2024/4/30
 */
public interface WechatMpService {

    /**
     * 获取二维码地址
     * @return 二维码地址
     */
    String getQRCodeUrl();

    /**
     * 获取snsapi_userinfo二维码地址
     *
     * @return AuthorizationUrl
     */
    String getAuthorizationUrl();

    /**
     * 获取access_token
     *
     * @param code code
     * @return WxOAuth2AccessToken
     */
    WxOAuth2AccessToken getAccessToken(String code) throws WxErrorException;

    /**
     * 验证state
     *
     * @param state state
     * @return true 有效
     */
    boolean checkState(String state);

    /**
     * 刷新access_token
     * @param refreshToken 刷新的token
     * @return WxOAuth2AccessToken
     */
    WxOAuth2AccessToken refreshAccessToken(String refreshToken) throws WxErrorException;

    /**
     * 获取用户信息
     *
     * @param oAuth2AccessToken oAuth2AccessToken
     * @param lang              语言
     * @return WxOAuth2UserInfo
     */
    WxOAuth2UserInfo getUserInfo(WxOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException;

    /**
     * 保存或更新用户信息
     *
     * @param userInfo 用户信息
     * @return MpUser
     */
    MpUser saveOrUpdateUserInfo(WxOAuth2UserInfo userInfo);

}
