package com.brycehan.boot.wechat.controller;

import com.brycehan.boot.wechat.service.WechatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 2023/7/12
 * @author Bryce Han
 */
@RequiredArgsConstructor
@RequestMapping(path = "/wechat")
@RestController
public class WechatController {

    private final WechatService wechatService;

    /**
     * 微信公众号验证
     *
     * @param appId appId
     * @param request 请求
     * @return echostr 字符串
     */
    @GetMapping(path = "/{appId}")
    public String appId(@PathVariable String appId, HttpServletRequest request) {
        wechatService.validRequest(appId);
        return request.getParameter("echostr");
    }

}
