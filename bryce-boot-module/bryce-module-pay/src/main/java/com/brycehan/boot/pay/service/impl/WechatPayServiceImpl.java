package com.brycehan.boot.pay.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.brycehan.boot.pay.config.PayProperties;
import com.brycehan.boot.pay.entity.PayOrder;
import com.brycehan.boot.pay.entity.PayRefund;
import com.brycehan.boot.pay.enums.OrderStatus;
import com.brycehan.boot.pay.enums.PayType;
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
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.*;
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
import java.util.HashMap;
import java.util.Map;
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

    private final NativePayService nativePayService;

    private final PayOrderService payOrderService;

    private final PayPaymentService payPaymentService;

    private final PayRefundService payRefundService;

    private final ReentrantLock orderLock = new ReentrantLock();
    private final ReentrantLock refundLock = new ReentrantLock();


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> nativePay(Long productId) {

        log.info("生成订单");
        PayOrder payOrder = payOrderService.createOrderByProductId(productId, PayType.WECHAT_PAY);
        String codeUrl = payOrder.getCodeUrl();
        if(StrUtil.isNotEmpty(codeUrl)){
            log.info("订单已存在，二维码已保存");

            // 返回二维码
            Map<String, Object> map = new HashMap<>();
            map.put("codeUrl", codeUrl);
            map.put("orderNo", payOrder.getOrderNo());
            return map;
        }

        log.info("调用统一下单API");
        PrepayRequest request = new PrepayRequest();
        PayProperties.WechatPay wechatPay = payProperties.getWechatPay();
        Amount amount = new Amount();
        amount.setTotal(payOrder.getTotalFee());
        request.setAmount(amount);
        request.setAppid(wechatPay.getAppid());
        request.setMchid(wechatPay.getMchId());
        request.setDescription(payOrder.getTitle());
        request.setNotifyUrl(wechatPay.getNotifyDomain().concat(WechatNotifyEndpoint.NATIVE_NOTIFY.getEndpoint()));
        request.setOutTradeNo(payOrder.getOrderNo());

        // 调用下单方法，得到应答
        PrepayResponse response = nativePayService.prepay(request);

        // 保存二维码
        payOrderService.saveCodeUrl(payOrder.getId(), response.getCodeUrl());

        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        Map<String, Object> map = new HashMap<>();
        map.put("codeUrl", response.getCodeUrl());
        map.put("orderNo", payOrder.getOrderNo());
        return map;
    }


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

    @Override
    public void cancelOrder(String orderNo) {

        // 调用微信支付的关单接口
        this.closeOrder(orderNo);

        // 更新商户端的订单状态
        this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.CANCEL);
    }

    @Override
    public Transaction queryOrder(String orderNo) {
        PayProperties.WechatPay wechatPay = payProperties.getWechatPay();

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        // 调用request.setXxx(val)设置所需参数
        request.setMchid(wechatPay.getMchId());
        request.setOutTradeNo(orderNo);

        // 调用接口
        return nativePayService.queryOrderByOutTradeNo(request);
    }

    @Override
    public void checkOrderStatus(String orderNo, boolean cancel) {

        log.warn("根据订单号核实订单状态 ---> {}", orderNo);

        // 调用微信支付查单接口
        Transaction transaction = this.queryOrder(orderNo);

        // 获取微信支付端的订单状态
        String tradeState = transaction.getTradeState().name();

        // 判断订单状态
        if(Transaction.TradeStateEnum.SUCCESS.name().equals(tradeState)) {
            log.warn("核实订单已支付 ---> {}", orderNo);

            // 如果确认订单已支付则更新本地订单状态
            this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);

            // 记录支付日志
            this.payPaymentService.createPayment(transaction);
        }

        if(cancel && Transaction.TradeStateEnum.NOTPAY.name().equals(tradeState)) {
            log.warn("核实订单未支付 ---> {}", orderNo);

            // 如果订单未支付，则调用关单接口
            this.closeOrder(orderNo);

            // 更新本地订单状态
            this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
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
        log.info("请求参数 ---> {}", JSONUtil.toJsonStr(request));

        try {
            // 完成签名关执行请求，并完成验签
            Refund refund = refundService.create(request);
            log.info("成功，退款返回结果 ---> {}", JSONUtil.toJsonStr(refund));

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
            log.info("查询退款成功，返回结果 ---> {}", JSONUtil.toJsonStr(refund));
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

    /**
     * 关单接口的调用
     *
     * @param orderNo 订单号
     */
    private void closeOrder(String orderNo) {
        PayProperties.WechatPay wechatPay = payProperties.getWechatPay();

        CloseOrderRequest request = new CloseOrderRequest();
        // 调用request.setXxx(val)设置所需参数，具体参数可见Request定义
        request.setMchid(wechatPay.getMchId());
        request.setOutTradeNo(orderNo);

        log.info("关单接口的调用，订单号 ---> {}", orderNo);
        // 调用接口
        nativePayService.closeOrder(request);
    }

}
