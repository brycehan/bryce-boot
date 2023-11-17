package com.brycehan.boot.wechat.service;

import com.brycehan.boot.common.util.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Service;

/**
 * @since 2023/7/12
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class WechatService {

    private final WxMpService mpService;

    public boolean validRequest(String appId) {
        HttpServletRequest request = ServletUtils.getRequest();
        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");
        return this.mpService.switchoverTo(appId).checkSignature(timestamp, nonce, signature);
    }
}
