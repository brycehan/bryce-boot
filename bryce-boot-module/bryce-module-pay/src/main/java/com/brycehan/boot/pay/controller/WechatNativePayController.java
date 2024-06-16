package com.brycehan.boot.pay.controller;

import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.pay.service.WechatNativePayService;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.model.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 网站微信支付APIv3
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Slf4j
@Tag(name = "微信支付APIv3-网站")
@RequestMapping("/pay/wechatNativePay")
@RestController
@RequiredArgsConstructor
@ConditionalOnBean(value = RSAAutoCertificateConfig.class)
public class WechatNativePayController {

    private final WechatNativePayService wechatNativePayService;

    /**
     * Native支付预下单
     *
     * @param productId 商品ID
     * @return 响应结果
     */
    @Operation(summary = "调用统一下单API，生成支付二维码")
    @OperateLog(type = OperateType.INSERT)
    @PostMapping(path = "/{productId}")
    public ResponseResult<Map<String, Object>> prepay(@PathVariable Long productId) {

        log.info("发起支付请求v3");

        // 返回支付二维码连接和订单号
        Map<String, Object> map = this.wechatNativePayService.prepay(productId);

        return ResponseResult.ok(map);
    }

    /**
     * 用户取消订单
     *
     * @return 响应结果
     */
    @Operation(summary = "用户取消订单")
    @OperateLog(type = OperateType.UPDATE)
    @PostMapping(path = "/cancel/{orderNo}")
    public ResponseResult<?> nativeCancel(@PathVariable String orderNo) {

        log.info("取消订单");
        this.wechatNativePayService.cancelOrder(orderNo);

        return ResponseResult.ok().setMessage("订单已取消");
    }

    /**
     * 根据订单号查询订单
     *
     * @param orderNo 订单号
     * @return 响应结果
     */
    @Operation(summary = "根据订单号查询订单")
    @OperateLog(type = OperateType.UPDATE)
    @PostMapping(path = "/query/{orderNo}")
    public ResponseResult<?> queryOrder(@PathVariable String orderNo) {

        log.info("查询订单，订单号：{}", orderNo);
        Transaction transaction = this.wechatNativePayService.queryOrder(orderNo);

        return ResponseResult.ok(transaction);
    }

}