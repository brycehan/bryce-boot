package com.brycehan.boot.pay.service;

import com.wechat.pay.java.service.payments.model.Transaction;

import java.util.Map;

/**
 * 微信Jsapi支付服务
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
public interface WechatJsapiPayService {

    /**
     * 创建订单，调用支付接口
     *
     * @param productId 商品ID
     * @return code_url 和 订单号
     */
    Map<String, Object> pay(Long productId);

    /**
     * 用户取消订单
     *
     * @param orderNo 订单号
     */
    void cancelOrder(String orderNo);

    /**
     * 根据商户订单号，查单接口
     *
     * @param orderNo 商户订单号
     * @return 订单状态
     */
    Transaction queryOrder(String orderNo);

    /**
     * 根据订单号查询微信支付查单接口，核实订单状态<br>
     * 如果订单已支付，则更新商户端订单状态，并记录支付日志
     *
     * @param orderNo 订单号
     * @param cancel 取消标识
     */
    void checkOrderStatus(String orderNo, boolean cancel);

}
