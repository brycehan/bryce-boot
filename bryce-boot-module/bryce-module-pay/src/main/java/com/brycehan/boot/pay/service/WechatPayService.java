package com.brycehan.boot.pay.service;

import com.brycehan.boot.pay.enums.PayType;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;

import java.util.Map;

/**
 * 微信支付服务
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
public interface WechatPayService {

    /**
     * Native支付，创建订单，调用Native支付接口
     *
     * @param productId 商品ID
     * @return code_url 和 订单号
     */
    Map<String, Object> nativePay(Long productId);

    /**
     * 回调通知处理订单状态为已支付
     *
     * @param transaction 参数
     */
    void processOrder(Transaction transaction);

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
     * @param refundNo 退款单号
     * @return 退款详情
     */
    Refund queryRefund(String refundNo);

    /**
     * 处理退款单
     *
     * @param refundNotification 通知参数
     */
    void processRefund(RefundNotification refundNotification);

    /**
     * 申请账单
     *
     * @param billDate 账单日期
     * @param type 账单类型（tradebill、fundflowbill）
     * @return 账单下载URL
     */
    String queryBill(String billDate, String type);

    /**
     * 下载账单
     *
     * @param billDate 账单日期
     * @param type 账单类型（tradebill、fundflowbill）
     * @return 账单Excel数据
     */
    String downloadBill(String billDate, String type);

}
