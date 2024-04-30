package com.brycehan.boot.third.login.controller;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.vo.LoginVo;
import com.brycehan.boot.third.login.service.WechatMpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Slf4j
@Tag(name = "微信登录API")
@RequestMapping("/third/login/wechat/mp")
@RestController
@RequiredArgsConstructor
public class WechatMpLoginController {

    private final WechatMpService wechatMpService;

    /**
     * 生成微信登录二维码
     *
     * @param response 返回图片流
     * @throws IOException IO异常
     */
    @GetMapping(path = "/img")
    public void wechatLogin(HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        String url = wechatMpService.getAuthorizationUrl();
        QrCodeUtil.generate(url,400,400, ImgUtil.IMAGE_TYPE_PNG, response.getOutputStream());
    }

    /**
     * 生成微信登录二维码
     *
     */
    @GetMapping
    public ResponseResult<String> getQRCodeUrl() {
        String url = wechatMpService.getQRCodeUrl();
        return ResponseResult.ok(url);
    }

    /**
     * 微信登录回调
     *
     * @param code  授权码
     * @param state 状态
     * @throws WxErrorException 微信异常
     */
    @GetMapping("/auth")
    public ResponseResult<LoginVo> auth(String code, String state) throws WxErrorException {
        // 校验state
        if (!this.wechatMpService.checkState(state)) {
            return ResponseResult.error();
        }

        // 获取访问token
        WxOAuth2AccessToken accessToken = wechatMpService.getAccessToken(code);
        log.info("第三方登录回调，token：{} openid：{}", accessToken.getAccessToken(), accessToken.getOpenId());

        // 获取用户信息
        WxOAuth2UserInfo userInfo = this.wechatMpService.getUserInfo(accessToken, "zh_CN");
        log.info("第三方登录回调，userInfo：{}", userInfo);

        // 保存或更新用户信息
        this.wechatMpService.saveOrUpdateUserInfo(userInfo);

        // 生成jwt 并返回
        return ResponseResult.ok();
    }

}