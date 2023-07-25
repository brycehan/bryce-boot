package com.brycehan.boot.wechat.controller;

import com.brycehan.boot.common.annotation.Log;
import com.brycehan.boot.common.enums.BusinessType;
import com.brycehan.boot.wechat.service.WechatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bryce Han
 * @since 2023/7/12
 */
@RequiredArgsConstructor
@RequestMapping(path = "/wechat")
@RestController
public class WechatController {

    private final WechatService wechatService;

    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @GetMapping(path = "/{appId}")
    public Boolean appId(@PathVariable String appId) {
        return wechatService.validRequest(appId);
    }

}
