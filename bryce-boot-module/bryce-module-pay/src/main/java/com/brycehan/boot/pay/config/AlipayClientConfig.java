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

    /*
     * 商家信息
     * 商户账号qassqe9609@sandbox.com
     * 登录密码111111
     * 商户PID2088721031562115
     * 账户余额1000000.00
     * 买家信息
     * 买家账号nacfqw2772@sandbox.com
     * 登录密码111111
     * 支付密码111111
     * 用户UID2088722031599893
     * 用户名称nacfqw2772
     * 证件类型IDENTITY_CARD
     * 证件账号261949192610069909
     */
    public static void main(String[] args) {
        String sha1 = "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";

        System.out.println(sha1.length());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if(i >= 10) {
                builder.append("1");
            }else {
                builder.append(sha1.charAt(i));
            }
        }
        System.out.println(builder.length());
        System.out.println(builder);

    }
}
