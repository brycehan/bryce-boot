package com.brycehan.boot.third.login.service.impl;

import com.brycehan.boot.common.base.RedisKeys;
import com.brycehan.boot.mp.common.config.MpProperties;
import com.brycehan.boot.mp.entity.po.MpUser;
import com.brycehan.boot.mp.service.MpUserService;
import com.brycehan.boot.third.login.service.WechatMpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Bryce Han
 * @since 2024/4/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WechatMpServiceImpl implements WechatMpService {

    private final WxMpService wxMpService;
    private final MpProperties mpProperties;
    private final StringRedisTemplate stringRedisTemplate;
    private final MpUserService mpUserService;

    /**
     * 获取二维码地址
     *
     * @return 二维码地址
     */
    @Override
    public String getQRCodeUrl() {
        // 生成 state 参数，用于防止 csrf
        String state = RandomStringUtils.randomAlphanumeric(64);

        String cacheKey = RedisKeys.getThirdLoginKey().concat(":").concat(state);
        this.stringRedisTemplate.opsForValue().set(cacheKey, "1", 10, TimeUnit.MINUTES);

        return wxMpService.buildQrConnectUrl(mpProperties.getRedirectUrl(),
                WxConsts.QrConnectScope.SNSAPI_LOGIN,
                state);
    }

    /**
     * 获取授权地址
     *
     * @return 授权地址
     */
    @Override
    public String getAuthorizationUrl() {
        // 生成 state 参数，用于防止 csrf
        String state = RandomStringUtils.randomAlphanumeric(64);

        String cacheKey = RedisKeys.getThirdLoginKey().concat(":").concat(state);
        this.stringRedisTemplate.opsForValue().set(cacheKey, "1", 10, TimeUnit.MINUTES);

        return wxMpService.getOAuth2Service().buildAuthorizationUrl(mpProperties.getRedirectUrl(),
                WxConsts.OAuth2Scope.SNSAPI_USERINFO, state);
    }

    /**
     * 校验 state
     *
     * @param state state
     * @return true/false
     */
    @Override
    public boolean checkState(String state) {
        String cacheKey = RedisKeys.getThirdLoginKey().concat(":").concat(state);
        String hasState = this.stringRedisTemplate.opsForValue().getAndDelete(cacheKey);

        return StringUtils.isNoneEmpty(hasState);
    }

    /**
     * 获取授权信息
     *
     * @param code 授权码
     * @return 授权信息
     */
    @Override
    public WxOAuth2AccessToken getAccessToken(String code) throws WxErrorException {
        return wxMpService.getOAuth2Service().getAccessToken(code);
    }

    /**
     * 刷新访问的token
     *
     * @param refreshToken 刷新的token
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxOAuth2AccessToken refreshAccessToken(String refreshToken) throws WxErrorException {
        return wxMpService.getOAuth2Service().refreshAccessToken(refreshToken);
    }

    /**
     * 获取用户信息
     *
     * @param oAuth2AccessToken 授权信息
     * @param lang              语言
     * @return 用户信息
     */
    @Override
    public WxOAuth2UserInfo getUserInfo(WxOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException {
        return wxMpService.getOAuth2Service().getUserInfo(oAuth2AccessToken, lang);
    }

    /**
     * 保存用户信息
     *
     * @param userInfo 用户信息
     * @return 用户信息
     */
    @Override
    public MpUser saveOrUpdateUserInfo(WxOAuth2UserInfo userInfo) {
        MpUser mpUser = this.mpUserService.getByOpenid(userInfo.getOpenid());

        // 如果用户不存在，则添加
        if (mpUser == null) {
            log.info("添加用户信息，openid：{}", userInfo.getOpenid());
            WxMpUser wxMpUser = new WxMpUser();
            BeanUtils.copyProperties(userInfo, wxMpUser);
            mpUser = MpUser.create(wxMpUser);
            this.mpUserService.save(MpUser.create(wxMpUser));
            return mpUser;
        }

        log.info("更新用户信息，openid：{}",userInfo.getOpenid());

        BeanUtils.copyProperties(userInfo, mpUser);

        this.mpUserService.saveOrUpdate(mpUser);

        return mpUser;
    }

}
