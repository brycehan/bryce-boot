package com.brycehan.boot.pay.service.impl;

import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.util.SecureUtils;
import com.brycehan.boot.pay.common.OrderStatus;
import com.brycehan.boot.pay.common.PayType;
import com.brycehan.boot.pay.common.WechatNotifyEndpoint;
import com.brycehan.boot.pay.common.config.PayProperties;
import com.brycehan.boot.pay.common.config.WechatPayConfig;
import com.brycehan.boot.pay.entity.po.PayOrder;
import com.brycehan.boot.pay.service.PayOrderService;
import com.brycehan.boot.pay.service.PayPaymentService;
import com.brycehan.boot.pay.service.WechatJsapiPayService;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
public class WechatJsapiPayServiceImpl implements WechatJsapiPayService {

    private final PayProperties payProperties;
    private final JsapiService jsapiService;
    private final PayOrderService payOrderService;
    private final PayPaymentService payPaymentService;

    /**
     * 创建订单
     *
     * @param productId 商品ID
     * @return 订单信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> pay(Long productId) {

        log.info("生成订单");
        PayOrder payOrder = payOrderService.createOrderByProductId(productId, PayType.WECHAT_JSAPI_PAY);

        log.info("调用统一下单API");
        PrepayRequest request = new PrepayRequest();
        PayProperties.WechatPay wechatPay = payProperties.getWechatPay();
        Amount amount = new Amount();
        amount.setTotal(payOrder.getTotalFee());
        request.setAmount(amount);
        request.setAppid(wechatPay.getMaAppid());
        request.setMchid(wechatPay.getMchId());
        request.setDescription(payOrder.getTitle());
        request.setNotifyUrl(wechatPay.getNotifyDomain().concat(WechatNotifyEndpoint.WECHAT_NATIVE_PAY_NOTIFY.getEndpoint()));
        request.setOutTradeNo(payOrder.getOrderNo());
        // 设置附加数据
        request.setAttach(payOrder.getPaymentType());

        // 设置支付者
        Payer payer = new Payer();
        payer.setOpenid(LoginUserContext.currentOpenId());
        request.setPayer(payer);

        // 调用下单方法，得到应答
        PrepayResponse response = jsapiService.prepay(request);

        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        Map<String, Object> map = new HashMap<>();

        long timeStamp = System.currentTimeMillis() / 1000;
        String nonceStr = RandomStringUtils.randomAlphanumeric(32);

        map.put("timeStamp", timeStamp);
        map.put("nonceStr", nonceStr);
        map.put("package", "prepay_id=".concat(response.getPrepayId()));

        String appid = wechatPay.getMaAppid();
        PrivateKey privateKey = WechatPayConfig.getPrivateKey(wechatPay.getPrivateKeyPath());
        String signatureStr = Stream.of(appid, String.valueOf(timeStamp), nonceStr, "prepay_id=".concat(response.getPrepayId()))
                .collect(Collectors.joining("\n", "", "\n"));

        map.put("signType", "RSA");
        map.put("paySign", SecureUtils.getWechatPaySign(signatureStr, privateKey));
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
        return jsapiService.queryOrderByOutTradeNo(request);
    }

    @Override
    public void checkOrderStatus(String orderNo, boolean cancel) {

        log.warn("checkOrderStatus，根据订单号核实订单状态 ---> {}", orderNo);

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
            log.warn("checkOrderStatus，核实订单未支付 ---> {}", orderNo);

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
        jsapiService.closeOrder(request);
    }

}
