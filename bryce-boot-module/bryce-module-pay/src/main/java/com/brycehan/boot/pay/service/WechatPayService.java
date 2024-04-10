package com.brycehan.boot.pay.service;

import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;

/**
 * 微信支付服务
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
public interface WechatPayService {

    /**
     * 回调通知处理订单状态为已支付
     *
     * @param transaction 参数
     */
    void processOrder(Transaction transaction);

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
