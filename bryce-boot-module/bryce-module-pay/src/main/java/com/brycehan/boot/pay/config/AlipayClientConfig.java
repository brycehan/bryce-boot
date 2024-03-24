package com.brycehan.boot.pay.config;

import com.alipay.api.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付宝配置
 *
 * @author Bryce Han
 * @since 2024/3/12
 */
@Configuration
@ConditionalOnProperty(name = "bryce.pay.alipay.enabled", havingValue = "true")
public class AlipayClientConfig {

    @Bean
    public AlipayClient alipayClient(PayProperties payProperties) throws AlipayApiException {
        PayProperties.Alipay alipay = payProperties.getAlipay();

        AlipayConfig alipayConfig = new AlipayConfig();
        //设置网关地址
        alipayConfig.setServerUrl(alipay.getGatewayUrl());
        //设置应用APPID
        alipayConfig.setAppId(alipay.getAppId());
        //设置应用私钥
        alipayConfig.setPrivateKey(alipay.getMerchantPrivateKey());
        //设置请求格式，固定值json
        alipayConfig.setFormat(AlipayConstants.FORMAT_JSON);
        //设置字符集
        alipayConfig.setCharset(AlipayConstants.CHARSET_UTF8);
        //设置支付宝公钥
        alipayConfig.setAlipayPublicKey(alipay.getAlipayPublicKey());
        //设置签名类型
        alipayConfig.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
        //构造client
        return new DefaultAlipayClient(alipayConfig);
    }

}
