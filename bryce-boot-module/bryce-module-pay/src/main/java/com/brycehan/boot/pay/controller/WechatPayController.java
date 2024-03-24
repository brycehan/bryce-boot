package com.brycehan.boot.pay.controller;

import cn.hutool.extra.servlet.JakartaServletUtil;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.pay.service.WechatPayService;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.http.Constant;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 网站微信支付APIv3
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Slf4j
@Tag(name = "网站微信支付APIv3", description = "wechatPay")
@RequestMapping("/pay/wechatPay")
@RestController
@RequiredArgsConstructor
@ConditionalOnBean(value = RSAAutoCertificateConfig.class)
public class WechatPayController {

    private final WechatPayService wechatPayService;

    private final RSAAutoCertificateConfig config;

    /**
     * Native支付预下单
     *
     * @param productId 商品ID
     * @return 响应结果
     */
    @Operation(summary = "调用统一下单API，生成支付二维码")
    @OperateLog(type = OperateType.INSERT)
    @PostMapping(path = "/native/{productId}")
    public ResponseResult<Map<String, Object>> nativePay(@PathVariable Long productId) {

        log.info("发起支付请求v3");

        // 返回支付二维码连接和订单号
        Map<String, Object> map = this.wechatPayService.nativePay(productId);

        return ResponseResult.ok(map);
    }

    /**
     * 支付通知
     * 微信支付通过支付通知接口，将用户支付成功消息通知给商户
     *
     * @return 响应结果
     */
    @Operation(summary = "支付通知")
    @OperateLog(type = OperateType.UPDATE)
    @PostMapping(path = "/native/notify")
    public ResponseEntity<?> nativeNotify(HttpServletRequest request) {

        log.info("支付通知处理开始");

        RequestParam requestParam = getRequestParam(request);
        // 初始化 NotificationParser
        NotificationParser parser = new NotificationParser(config);

        try {
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            Transaction transaction = parser.parse(requestParam, Transaction.class);
            log.info("支付通知，订单号：{}", transaction.getOutTradeNo());
            // 处理订单
            wechatPayService.processOrder(transaction);
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            log.error("支付通知签名验证失败", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("支付通知处理失败", e);
            // 如果处理失败，应返回 4xx/5xx 的状态码，例如 500 INTERNAL_SERVER_ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // 处理成功，返回 200 OK 状态码
        return ResponseEntity.status(HttpStatus.OK).build();
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
        this.wechatPayService.cancelOrder(orderNo);

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
        Transaction transaction = this.wechatPayService.queryOrder(orderNo);

        return ResponseResult.ok(transaction);
    }

    /**
     * 申请退款
     *
     * @param orderNo 订单号
     * @param reason 退款原因
     * @return 响应结果
     */
    @Operation(summary = "申请退款")
    @OperateLog(type = OperateType.INSERT)
    @PostMapping(path = "/refund/{orderNo}/{reason}")
    public ResponseResult<?> applyRefund(@PathVariable String orderNo, @PathVariable String reason) {

        log.info("申请退款，订单号：{}，原因：{}", orderNo, reason);

        this.wechatPayService.refund(orderNo, reason);

        return ResponseResult.ok();
    }

    /**
     * 查询退款
     *
     * @param refundNo 退款单号
     * @return 响应结果
     */
    @Operation(summary = "查询退款")
    @GetMapping(path = "/queryRefund/{refundNo}")
    public ResponseResult<Refund> queryRefund(@PathVariable String refundNo) {

        log.info("查询退款，退款号：{}", refundNo);

        Refund refund = this.wechatPayService.queryRefund(refundNo);

        return ResponseResult.ok(refund);
    }

    /**
     * 退款结果通知，退款状态改变后，微信会把相关退款结果发送给商户
     *
     * @return 响应结果
     */
    @Operation(summary = "退款结果通知")
    @PostMapping(path = "/refund/notify")
    public ResponseEntity<Object> refundNotify(HttpServletRequest request) {

        log.info("退款结果通知处理开始");

        // 构造 RequestParam
        RequestParam requestParam = getRequestParam(request);

        // 初始化 NotificationParser
        NotificationParser parser = new NotificationParser(config);

        try {
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            RefundNotification refundNotification = parser.parse(requestParam, RefundNotification.class);

            // 处理订单
            wechatPayService.processRefund(refundNotification);
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            log.error("退款结果通知签名验证失败", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("退款结果通知处理失败", e);
            // 如果处理失败，应返回 4xx/5xx 的状态码，例如 500 INTERNAL_SERVER_ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // 处理成功，返回 200 OK 状态码
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 获取账单URL
     *
     * @param billDate 账单日期
     * @param type 账单类型（交易账单tradebill、资金账单fundflowbill）
     * @return 响应结果
     */
    @Operation(summary = "获取账单URL")
    @GetMapping(path = "/queryBill/{billDate}/{type}")
    public ResponseResult<String> queryBill(@PathVariable String billDate, @PathVariable String type) {

        log.info("获取账单URL");

        String downloadUrl = wechatPayService.queryBill(billDate, type);

        return ResponseResult.ok(downloadUrl);
    }

    /**
     * 下载账单
     *
     * @param billDate 账单日期
     * @param type 账单类型（tradebill、fundflowbill）
     * @return 响应结果
     */
    @Operation(summary = "下载账单", description = "账单日期例如：2024-03-13, 账单类型（tradebill、fundflowbill）")
    @GetMapping(path = "/downloadBill/{billDate}/{type}")
    public ResponseResult<String> downloadBill(@PathVariable String billDate, @PathVariable String type) {

        log.info("下载账单");

        String data = wechatPayService.downloadBill(billDate, type);

        return ResponseResult.ok(data);
    }

    /**
     * 获取回调请求头参数
     *
     * @param request 回调请求
     * @return 请求参数，用于验签
     */
    private RequestParam getRequestParam(HttpServletRequest request) {
        // 处理通知参数
        String serialNumber = request.getHeader(Constant.WECHAT_PAY_SERIAL);
        String nonce = request.getHeader(Constant.WECHAT_PAY_NONCE);
        String signature = request.getHeader(Constant.WECHAT_PAY_SIGNATURE);
        String timestamp = request.getHeader(Constant.WECHAT_PAY_TIMESTAMP);
        String body = JakartaServletUtil.getBody(request);

        // 构造 RequestParam
        return new RequestParam.Builder()
                .serialNumber(serialNumber)
                .nonce(nonce)
                .signature(signature)
                .timestamp(timestamp)
                .body(body)
                .build();
    }

}