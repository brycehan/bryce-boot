package com.brycehan.boot.pay.service.impl;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.pay.config.PayProperties;
import com.brycehan.boot.pay.entity.PayOrder;
import com.brycehan.boot.pay.enums.OrderStatus;
import com.brycehan.boot.pay.enums.PayType;
import com.brycehan.boot.pay.enums.WechatNotifyEndpoint;
import com.brycehan.boot.pay.service.PayOrderService;
import com.brycehan.boot.pay.service.PayPaymentService;
import com.brycehan.boot.pay.service.WechatNativePayService;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


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
public class WechatNativePayServiceImpl implements WechatNativePayService {

    private final PayProperties payProperties;
    private final NativePayService nativePayService;
    private final PayOrderService payOrderService;
    private final PayPaymentService payPaymentService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> prepay(Long productId) {

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
        request.setNotifyUrl(wechatPay.getNotifyDomain().concat(WechatNotifyEndpoint.WECHAT_NATIVE_PAY_NOTIFY.getEndpoint()));
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
