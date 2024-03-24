package com.brycehan.boot.pay.service;

import java.util.Map;

/**
 * 支付宝服务
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
public interface AlipayService {

    /**
     * 电脑网站支付，创建订单，调用trade.page.pay支付接口
     *
     * @param productId 商品ID
     * @return formStr 表单字符串
     */
    String pagePay(Long productId);

    /**
     * 回调通知处理订单状态为已支付
     *
     * @param params 参数
     */
    void processOrder(Map<String, String> params);

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
     * @return 订单查询结果，如果返回null则表示支付宝端尚未创建订单
     */
    String queryOrder(String orderNo);

    /**
     * 根据订单号查询支付宝查单接口，核实订单状态<br>
     * 如果订单未创建，则更新商户端订单状态<br>
     * 如果订单未支付，并且cancel为true时，则调用关单接口关闭订单，并更新商户端订单状态<br>
     * 如果订单已支付，则更新商户端订单状态，并记录支付日志
     *
     * @param orderNo 订单号
     * @param cancel 取消标识
     */
    void checkOrderStatus(String orderNo, boolean cancel);

    /**
     * 申请退款
     *
     * @param orderNo 订单号
     * @param reason 退款原因
     */
    void refund(String orderNo, String reason);

    /**
     * 查询退款接口调用
     *
     * @param orderNo 订单号
     * @return 退款详情
     */
    String queryRefund(String orderNo);

    /**
     * 申请账单
     *
     * @param billDate 账单日期
     * @param type 账单类型
     * @return 账单下载URL
     */
    String queryBill(String billDate, String type);

}
