package com.brycehan.boot.pay.service.impl;

import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.pay.config.PayProperties;
import com.brycehan.boot.pay.entity.PayRefund;
import com.brycehan.boot.pay.enums.OrderStatus;
import com.brycehan.boot.pay.enums.WechatNotifyEndpoint;
import com.brycehan.boot.pay.service.PayOrderService;
import com.brycehan.boot.pay.service.PayPaymentService;
import com.brycehan.boot.pay.service.PayRefundService;
import com.brycehan.boot.pay.service.WechatPayService;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.billdownload.BillDownloadService;
import com.wechat.pay.java.service.billdownload.BillDownloadServiceExtension;
import com.wechat.pay.java.service.billdownload.DigestBillEntity;
import com.wechat.pay.java.service.billdownload.model.BillType;
import com.wechat.pay.java.service.billdownload.model.GetFundFlowBillRequest;
import com.wechat.pay.java.service.billdownload.model.GetTradeBillRequest;
import com.wechat.pay.java.service.billdownload.model.QueryBillEntity;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 订单服务实现
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnBean(value = RSAAutoCertificateConfig.class)
public class WechatPayServiceImpl implements WechatPayService {

    private final PayProperties payProperties;

    private final RSAAutoCertificateConfig config;

    private final PayOrderService payOrderService;

    private final PayPaymentService payPaymentService;

    private final PayRefundService payRefundService;

    private final ReentrantLock orderLock = new ReentrantLock();
    private final ReentrantLock refundLock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processOrder(Transaction transaction) {
        log.info("处理订单");

        // 订单编号
        String orderNo = transaction.getOutTradeNo();

        // 成功获取则立即返回true，获取失败则立即返回false
        if (orderLock.tryLock()) {
            try {
                // 处理重复的通知，接口调用的幂等性
                String orderStatus = this.payOrderService.getOrderStatus(orderNo);
                if(!OrderStatus.NO_PAY.getType().equals(orderStatus)) {
                    return;
                }

                // 更新订单状态
                this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);

                // 记录支付日志
                this.payPaymentService.createPayment(transaction);
            } finally {
                orderLock.unlock();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refund(String orderNo, String reason) {

        log.info("创建退款单记录");
        // 根据订单号创建退款单
        PayRefund payRefund = this.payRefundService.createRefundByOrderNo(orderNo, reason);

        // 不能退款或已经退了款
        if(payRefund == null) {
            return;
        }

        log.info("调用退款API");
        RefundService refundService = new RefundService.Builder().config(config).build();
        // 请求参数
        CreateRequest request = getCreateRequest(orderNo, reason, payRefund);
        log.info("请求参数 ---> {}", JsonUtils.writeValueAsString(request));

        try {
            // 完成签名关执行请求，并完成验签
            Refund refund = refundService.create(request);
            log.info("成功，退款返回结果 ---> {}", JsonUtils.writeValueAsString(refund));

            // 更新订单状态
            this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.REFUND_PROCESSING);

            // 更新退款单
            this.payRefundService.updateRefund(refund);

        } catch (Exception e) {
            log.error("申请退款失败", e);
        }
    }

    @Override
    public Refund queryRefund(String refundNo) {

        log.info("查询退款接口调用，退款单号：{}", refundNo);

        RefundService refundService = new RefundService.Builder().config(config).build();
        // 请求参数
        QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
        request.setOutRefundNo(refundNo);

        try {
            log.info("调用查询退款API");

            // 完成签名关执行请求，并完成验签
            Refund refund = refundService.queryByOutRefundNo(request);
            log.info("查询退款成功，返回结果 ---> {}", JsonUtils.writeValueAsString(refund));
            return refund;
        } catch (Exception e) {
            log.error("查询退款失败", e);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processRefund(RefundNotification refundNotification) {

        log.info("处理退款单");
        String orderNo = refundNotification.getOutTradeNo();

        if (refundLock.tryLock()) {
            try {
                String orderStatus = this.payOrderService.getOrderStatus(orderNo);

                if(!OrderStatus.REFUND_PROCESSING.getType().equals(orderStatus)) {
                    return;
                }

                OrderStatus orderStatusType;

                if(Status.SUCCESS.equals(refundNotification.getRefundStatus())) { // 退款成功时
                    orderStatusType = OrderStatus.REFUND_SUCCESS;
                } else if (Status.ABNORMAL.equals(refundNotification.getRefundStatus())){ // 退款异常时
                    orderStatusType = OrderStatus.REFUND_ABNORMAL;
                } else {
                    return;
                }

                // 更新订单状态
                this.payOrderService.updateStatusByOrderNo(orderNo, orderStatusType);

                // 更新退款单
                this.payRefundService.updateRefund(refundNotification);

            } finally {
                refundLock.unlock();
            }
        }
    }

    @Override
    public String queryBill(String billDate, String type) {
        // 初始化服务
        BillDownloadService billDownloadService = new BillDownloadService.Builder().config(config).build();

        if("tradebill".equals(type)) {

            GetTradeBillRequest request = new GetTradeBillRequest();
            request.setBillDate(billDate);
            request.setBillType(BillType.ALL);

            // 申请交易账单API
            QueryBillEntity tradeBill = billDownloadService.getTradeBill(request);

            return tradeBill.getDownloadUrl();
        } else if("fundflowbill".equals(type)){

            GetFundFlowBillRequest request = new GetFundFlowBillRequest();
            request.setBillDate(billDate);

            // 申请资金账单API
            QueryBillEntity fundFlowBill = billDownloadService.getFundFlowBill(request);
            return fundFlowBill.getDownloadUrl();
        }

        throw new RuntimeException("不支持的账单类型：".concat(type));
    }

    @Override
    public String downloadBill(String billDate, String type) {
        BillDownloadServiceExtension service = new BillDownloadServiceExtension.Builder().config(config).build();
        DigestBillEntity bill;

        if("tradebill".equals(type)) {

            GetTradeBillRequest request = new GetTradeBillRequest();
            request.setBillDate(billDate);
            request.setBillType(BillType.ALL);

            // 申请交易账单API
            bill = service.getTradeBill(request);
        } else if("fundflowbill".equals(type)){

            GetFundFlowBillRequest request = new GetFundFlowBillRequest();
            request.setBillDate(billDate);

            // 申请资金账单API
            bill = service.getFundFlowBill(request);
        } else {

            throw new RuntimeException("不支持的账单类型：".concat(type));
        }

        if (bill.verifyHash()) throw new RuntimeException("下载出错了");

        ByteArrayOutputStream result = new ByteArrayOutputStream();

        try (InputStream inputStream = bill.getInputStream()) {

                byte[] buffer = new byte[16384];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, bytesRead);
                }
                return result.toString(StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private CreateRequest getCreateRequest(String orderNo, String reason, PayRefund payRefund) {
        PayProperties.WechatPay wechatPay = payProperties.getWechatPay();

        CreateRequest request = new CreateRequest();
        request.setOutTradeNo(orderNo); // 订单号
        request.setOutRefundNo(payRefund.getRefundNo()); // 退款单号
        request.setReason(reason); // 退款原因
        // 退款通知
        request.setNotifyUrl(wechatPay.getNotifyDomain().concat(WechatNotifyEndpoint.REFUND_NOTIFY.getEndpoint()));

        AmountReq amount = new AmountReq();
        amount.setRefund(payRefund.getRefund().longValue()); // 退款金额
        amount.setTotal(payRefund.getTotalFee().longValue()); // 原订单金额
        amount.setCurrency("CNY"); // 退款币种
        request.setAmount(amount);
        return request;
    }

}
