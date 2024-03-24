package com.brycehan.boot.pay.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.brycehan.boot.pay.config.PayProperties;
import com.brycehan.boot.pay.entity.PayOrder;
import com.brycehan.boot.pay.entity.PayRefund;
import com.brycehan.boot.pay.enums.AlipayTradeState;
import com.brycehan.boot.pay.enums.OrderStatus;
import com.brycehan.boot.pay.enums.PayType;
import com.brycehan.boot.pay.service.AlipayService;
import com.brycehan.boot.pay.service.PayOrderService;
import com.brycehan.boot.pay.service.PayPaymentService;
import com.brycehan.boot.pay.service.PayRefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 支付宝服务实现
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnBean(value = AlipayClient.class)
public class AlipayServiceImpl implements AlipayService {

    private final PayProperties payProperties;

    private final AlipayClient alipayClient;

    private final PayOrderService payOrderService;

    private final PayPaymentService payPaymentService;

    private final PayRefundService payRefundService;

    private final ReentrantLock orderLock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String pagePay(Long productId) {


        try {
            log.info("生成订单");
            PayOrder payOrder = payOrderService.createOrderByProductId(productId, PayType.ALI_PAY);

            PayProperties.Alipay alipay = payProperties.getAlipay();
            // 请求参数封装
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            //异步通知地址，仅支持http/https，公网可访问
            request.setNotifyUrl(alipay.getNotifyUrl());
            //同步跳转地址，仅支持http/https
            request.setReturnUrl(alipay.getReturnUrl());

            // 必传参数
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setOutTradeNo(payOrder.getOrderNo()); // 商户订单号，商家自定义，保持唯一性
            BigDecimal totalAmount = new BigDecimal(payOrder.getTotalFee()).divide(new BigDecimal(100));
            model.setTotalAmount(totalAmount.toPlainString()); // 支付金额，单位元
            model.setSubject(payOrder.getTitle()); // 订单标题，不可使用特殊符号
            model.setProductCode("FAST_INSTANT_TRADE_PAY");// 电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
            request.setBizModel(model);

            // 执行请求，调用支付宝接口
            AlipayTradePagePayResponse response =  alipayClient.pageExecute(request, "POST");
            String pageRedirectionData = response.getBody();
            System.out.println(pageRedirectionData);

            if (response.isSuccess()) {
               log.info("调用成功，返回结果：{}", response.getBody());
               return response.getBody();
            } else {
                log.error("调用失败，返回码：{}，返回描述：{}", response.getCode(), response.getMsg());
                throw new RuntimeException("创建支付宝支付交易失败");
            }
        } catch (AlipayApiException e) {
            log.error("调用失败，返回码：{}，返回描述：{}", e.getErrCode(), e.getErrCode());
            throw new RuntimeException("创建支付宝支付交易失败");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processOrder(Map<String, String> params) {
        log.info("处理订单");

        // 获取订单号
        String orderNo = params.get("out_trade_no");

        // 成功获取则立即返回true，获取失败则立即返回false
        if (orderLock.tryLock()) {
            try {
                // 处理重复的通知，接口调用的幂等性
                String orderStatus = this.payOrderService.getOrderStatus(orderNo);
                if (!OrderStatus.NO_PAY.getType().equals(orderStatus)) {
                    return;
                }

                // 更新订单状态
                this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);

                // 记录支付日志
                this.payPaymentService.createPayment(params);
            } finally {
                orderLock.unlock();
            }
        }
    }

    @Override
    public void cancelOrder(String orderNo) {

        // 调用支付宝提供的统一收单交易关闭接口
        this.closeOrder(orderNo);

        // 更新商户端的订单状态
        this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.CANCEL);
    }

    @Override
    public String queryOrder(String orderNo) {

        log.info("支付宝查单接口调用，订单号：{}", orderNo);
        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(orderNo);
            request.setBizModel(model);

            // 调用接口
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                log.info("调用成功，返回结果：{}", response.getBody());
                return  response.getBody();
            } else {
                log.error("调用失败，返回码：{}，返回描述：{}", response.getCode(), response.getMsg());
                return null; // 订单不存在
            }
        } catch (AlipayApiException e) {
            log.error("支付宝调用失败，返回码：{}，返回描述：{}", e.getErrCode(), e.getErrMsg());
            throw new RuntimeException("支付宝查单接口调用失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkOrderStatus(String orderNo, boolean cancel) {

        log.warn("根据订单号核实订单状态，订单号：{}", orderNo);

        // 调用查单接口
        String result = this.queryOrder(orderNo);

        // 订单未创建
        if (result == null) {
            log.warn("核实订单未创建，订单号：{}", orderNo);
            // 更新本地订单状态
            // 如果确认订单已支付则更新本地订单状态
            this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
            return;
        }

        // 解析查单响应结果
        HashMap<String, JSONObject> resultMap = JSONUtil.toBean(result, HashMap.class);
        JSONObject alipayTradeQueryResponse = resultMap.get("alipay_trade_query_response");
        HashMap alipayTradeQueryResponseMap = JSONUtil.toBean(alipayTradeQueryResponse, HashMap.class);

        String tradeStatus = (String) alipayTradeQueryResponse.get("trade_status");

        // 判断订单状态
        if (cancel && AlipayTradeState.NO_PAY.getValue().equals(tradeStatus)) {
            log.warn("核实订单未支付，订单号：{}", orderNo);

            // 如果订单未支付，则调用关单接口
            this.closeOrder(orderNo);

            // 更新本地订单状态
            this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
        }

        if (AlipayTradeState.SUCCESS.getValue().equals(tradeStatus)) {
            log.warn("核实订单已支付，订单号：{}", orderNo);

            // 如果确认订单已支付，则更新本地订单状态
            this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);

            // 记录支付日志
            this.payPaymentService.createPayment(alipayTradeQueryResponseMap);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refund(String orderNo, String reason) {

        log.info("创建退款单记录");

        try {
            // 根据订单号创建退款单
            PayRefund payRefund = this.payRefundService.createRefundByOrderNo(orderNo, reason);

            // 不能退款或已经退了款
            if(payRefund == null) {
                return;
            }

            log.info("调用退款API");
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

            // 组装请求参数
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setOutTradeNo(orderNo);
            BigDecimal refund = new BigDecimal(payRefund.getRefund()).divide(new BigDecimal(100));
            model.setRefundAmount(refund.toPlainString());
            model.setRefundReason(reason);

            request.setBizModel(model);

            // 执行请求，调用支付宝接口
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                log.info("调用成功，返回结果：{}", response.getBody());

                // 更新订单状态
                this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.REFUND_SUCCESS);

                // 更新退款单
                this.payRefundService.updateRefund(
                        payRefund.getRefundNo(),
                        response.getBody(),
                        AlipayTradeState.REFUND_SUCCESS);
            } else {
                log.info("调用失败，返回码：{}，返回消息：{}", response.getCode(), response.getMsg());

                // 更新订单状态
                this.payOrderService.updateStatusByOrderNo(orderNo, OrderStatus.REFUND_ABNORMAL);

                // 更新退款单
                this.payRefundService.updateRefund(
                        payRefund.getRefundNo(),
                        response.getBody(),
                        AlipayTradeState.REFUND_ERROR);
            }
        } catch (AlipayApiException e) {
            log.error(e.getMessage());
            throw new RuntimeException("创建退款单失败");
        }
    }

    @Override
    public String queryRefund(String orderNo) {

        log.info("查询退款接口调用，订单号：{}", orderNo);

        try {
            AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();

            AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
            model.setOutTradeNo(orderNo);
            model.setOutRequestNo(orderNo);

            request.setBizModel(model);

            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                log.info("调用成功，返回结果：{}", response.getBody());
                return response.getBody();
            } else {
                log.info("调用失败，返回码：{}，返回消息：{}", response.getCode(), response.getMsg());
                return null; // 订单不存在
            }
        } catch (AlipayApiException e) {
            log.info("调用失败，返回码：{}，返回消息：{}", e.getErrCode(), e.getErrMsg());
            throw new RuntimeException("统一收单交易退款查询失败");
        }
    }

    @Override
    public String queryBill(String billDate, String type) {

        try {
            AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();

            AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
            model.setBillType(type);
            model.setBillDate(billDate);
            request.setBizModel(model);

            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);

            if (response.isSuccess()) {
                log.info("调用成功，返回结果：{}", response.getBody());

                // 获取账号下载地址
                HashMap<String, JSONObject> resultMap = JSONUtil.toBean(response.getBody(), HashMap.class);
                JSONObject billDownloadurlQueryResponse = resultMap.get("alipay_data_dataservice_bill_downloadurl_query_response");

                return (String) billDownloadurlQueryResponse.get("bill_download_url");
            } else {
                log.info("调用失败，返回码：{}，返回消息：{}", response.getCode(), response.getMsg());
                throw new RuntimeException("申请账号失败");
            }

        } catch (AlipayApiException e) {
            log.info("调用失败，返回码：{}，返回消息：{}", e.getErrCode(), e.getErrMsg());
            throw new RuntimeException("申请账号失败");
        }

    }

    /**
     * 关单接口的调用
     *
     * @param orderNo 订单号
     */
    private void closeOrder(String orderNo) {

        log.info("支付宝关单接口的调用，订单号 ---> {}", orderNo);

        try {
            AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            AlipayTradeCloseModel model = new AlipayTradeCloseModel();
            model.setOutTradeNo(orderNo);
            request.setBizModel(model);
            // 调用接口
            AlipayTradeCloseResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                log.info("调用成功，返回结果：{}", response.getBody());
            } else {
                log.error("调用失败，返回码：{}，返回描述：{}", response.getCode(), response.getMsg());
                // 不登录和不扫码时，支付宝不创建订单，不需要关单
            }
        } catch (AlipayApiException e) {
            log.error("支付宝调用失败，返回码：{}，返回描述：{}", e.getErrCode(), e.getErrMsg());
            throw new RuntimeException("支付宝关单接口调用失败");
        }
    }

}
