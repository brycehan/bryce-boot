package com.brycehan.boot.pay.common.config;

import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.util.PemUtil;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.security.PrivateKey;

/**
 * 微信支付配置
 *
 * @author Bryce Han
 * @since 2024/2/29
 */
@Configuration
@ConditionalOnProperty(name = "bryce.pay.wechat-pay.enabled", havingValue = "true")
public class WechatPayConfig {

    /**
     * 使用自动更新平台证书的RSA配置
     * 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
     *
     * @return 微信商户RSA配置
     */
    @Bean
    public RSAAutoCertificateConfig mchConfig(PayProperties payProperties) {
        PayProperties.WechatPay wechatPay = payProperties.getWechatPay();

        return new RSAAutoCertificateConfig.Builder()
                .merchantId(wechatPay.getMchId())
                .privateKey(getPrivateKey(wechatPay.getPrivateKeyPath()))
                .merchantSerialNumber(wechatPay.getMchSerialNo())
                .apiV3Key(wechatPay.getApiV3Key())
                .build();
    }

    /**
     * 微信支付服务
     *
     * @return 微信支付服务
     */
    @Bean
    @ConditionalOnBean(value = RSAAutoCertificateConfig.class)
    public NativePayService nativePayService(RSAAutoCertificateConfig config){
        return new NativePayService.Builder()
                .config(config)
                .build();
    }

    /**
     * 微信Jsapi支付服务
     *
     * @return 微信Jsapi支付服务
     */
    @Bean
    @ConditionalOnBean(value = RSAAutoCertificateConfig.class)
    public JsapiService jsapiPayService(RSAAutoCertificateConfig config){
        return new JsapiService.Builder()
                .config(config)
                .build();
    }

    /**
     * 从classpath下获取私钥
     *
     * @param privateKeyPath classpath下文件路径
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String privateKeyPath){
        PrivateKey privateKey;
        try {
            File file = ResourceUtils.getFile(privateKeyPath);
            privateKey = PemUtil.loadPrivateKeyFromPath(file.getCanonicalPath());
        } catch (Exception e) {
            throw new RuntimeException("私钥文件不存在", e);
        }
        return  privateKey;
    }

}
