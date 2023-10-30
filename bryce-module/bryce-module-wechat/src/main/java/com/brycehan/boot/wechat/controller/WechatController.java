package com.brycehan.boot.wechat.controller;

import com.brycehan.boot.wechat.service.WechatService;
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

    @GetMapping(path = "/{appId}")
    public Boolean appId(@PathVariable String appId) {
        return wechatService.validRequest(appId);
    }

}
