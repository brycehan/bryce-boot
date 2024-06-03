package com.brycehan.boot.pay.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bryce Han
 * @since 2024/3/23
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bryce.pay")
public class PayProperties {

    private WechatPay wechatPay;

    private Alipay alipay;

    @Data
    public static class WechatPay {
        /**
         * 开关
         */
        private Boolean enabled;
        /**
         * 商户号
         */
        private String mchId;

        /**
         * 商户API证书序列号
         */
        private String mchSerialNo;

        /**
         * 商户私钥文件
         */
        private String privateKeyPath;


        /**
         * APIv3密钥
         */
        private String apiV3Key;

        /**
         * APPID
         */
        private String appid;

        /**
         * 小程序APPID
         */
        private String maAppid;

        /**
         * 微信服务器地址
         */
        private String domain;

        /**
         * 接收结果通知地址
         */
        private String notifyDomain;

        /**
         * APIv2密钥
         */
        private String partnerKey;

    }

    @Data
    public static class Alipay {
        /**
         * 开关
         */
        private Boolean enabled;

        /**
         * 应用ID，您的APPID，收款账号即是您的APPID对应支付宝账号
         */
        private String appId;

        /**
         * 商户PID，卖家支付宝账号ID
         */
        private String sellerId;

        /**
         * 支付宝网关
         */
        private String gatewayUrl;

        /**
         * 商户私钥，您的PKCS8格式RSA2私钥
         */
        private String merchantPrivateKey;

        /**
         * 支付宝公钥
         */
        private String alipayPublicKey;

        /**
         * 接口内容加密秘钥，对称秘钥
         */
        private String contentKey;

        /**
         * 页面跳转同步通知页面路径
         */
        private String returnUrl;

        /**
         * 服务器异步通知页面路径，需要http://格式的完整路径，不能加?id=123这类自定义参数
         */
        private String notifyUrl;
    }

}
