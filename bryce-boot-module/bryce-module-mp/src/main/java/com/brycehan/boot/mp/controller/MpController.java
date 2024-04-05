package com.brycehan.boot.mp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 微信API
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Slf4j
@Tag(name = "微信公众号API - 微信公众号服务会调用")
@RequestMapping("/mp")
@RestController
@RequiredArgsConstructor
public class MpController {

    private final WxMpService wxMpService;

    private final WxMpMessageRouter wxMpMessageRouter;

    /**
     * 微信公众号验证
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return echostr 字符串为验证成功
     */
    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String check(String signature,
                           String timestamp,
                           String nonce,
                           String echostr) {

        log.info("接收到来自微信服务器的认证消息：signature：{}，timestamp：{}，nonce：{}，echostr：{}",
                signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            log.warn("请求参数非法，请核实！");
            return "非法请求";
        }

        if (this.wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    /**
     * 接收微信服务器发送的各类消息
     *
     * @param requestBody 请求体
     * @return 响应结果
     */
    @Operation(summary = "接收微信服务器发送的各类消息", description = "公众号接入开发模式后才有效")
    @PostMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public String receive(@RequestBody String requestBody,
                                        String signature,
                                        String timestamp,
                                        String nonce,
                                        String openid,
                                        @RequestParam(name = "encrypt_type", required = false) String encryptType,
                                        @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        log.info("接收微信请求：openid：{}，signature：{}，encryptType：{}，msgSignature：{}，timestamp：{}，nonce：{}，requestBody：{}",
                openid, signature, encryptType, msgSignature, timestamp, nonce, requestBody);

        if (!this.wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        if (encryptType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);

            if(outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encryptType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
            log.debug("消息解密后的内容为：{}", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if(outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
        }

        log.debug("组装回复消息：{}", out);

        return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.wxMpMessageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }

        return null;
    }
}