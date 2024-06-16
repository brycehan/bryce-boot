package com.brycehan.boot.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.pay.common.config.PayProperties;
import com.brycehan.boot.pay.entity.po.PayOrder;
import com.brycehan.boot.pay.service.AlipayService;
import com.brycehan.boot.pay.service.PayOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付宝API
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Slf4j
@Tag(name = "支付宝API")
@RequestMapping("/pay/alipay")
@RestController
@RequiredArgsConstructor
@ConditionalOnBean(value = AlipayClient.class)
public class AlipayController {

    private final AlipayService alipayService;

    private final PayProperties payProperties;

    private final PayOrderService payOrderService;

    /**
     * 统一收单下单并支付页面接口的调用
     *
     * @param productId 商品ID
     * @return 响应结果
     */
    @Operation(summary = "统一收单下单并支付页面接口")
    @OperateLog(type = OperateType.INSERT)
    @PostMapping(path = "/trade/page/pay/{productId}")
    public ResponseResult<String> tradePagePay(@PathVariable Long productId) {

        log.info("统一收单下单并支付页面接口的调用");

        // 支付宝开放平台接受 request 请求对象后
        // 会为开发者生成一个html 形式的 form 表单，包含自动提交的脚本
        String formStr = this.alipayService.pagePay(productId);
        // 我们将form表单字符串返回给前端程序，之后前端将会调用自动提交脚本，进行表单的提交
        // 此时，表单会自动提交到action属性所指向的支付宝开放平台中,从而为用户展示一个支付页面
        return ResponseResult.ok(formStr);
    }

    /**
     * 支付通知
     * 支付宝通过支付通知接口，将用户支付成功消息通知给商户
     *
     * @return 响应结果
     */
    @Operation(summary = "支付通知")
    @OperateLog(type = OperateType.UPDATE)
    @PostMapping(path = "/trade/notify")
    public String tradeNotify(@RequestParam Map<String, String> params) {

        log.info("支付通知正在执行，参数：{}", params);

        String result = "failure";
        try {
            PayProperties.Alipay alipay = payProperties.getAlipay();
            // 异步通知验签
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    alipay.getAlipayPublicKey(),
                    AlipayConstants.CHARSET_UTF8,
                    AlipayConstants.SIGN_TYPE_RSA2); //调用SDK验证签名
            if(!signVerified){
                // 验签失败则记录异常日志，并在response中返回failure.
                log.error("支付宝支付成功异步通知验签失败！");
                return result;
            }

            // 验签成功后
            log.info("支付宝支付成功异步通知验签成功！");

            // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验
            // 1、商家需要验证该通知数据中的 out_trade_no 是否为商家系统中创建的订单号
            String outTradeNo = params.get("out_trade_no");
            PayOrder payOrder = payOrderService.getOrderByOrderNo(outTradeNo);
            if(payOrder == null) {
                log.error("订单不存在，订单号：{}", outTradeNo);
                return result;
            }

            // 2、判断 total_amount 是否确实为该订单的实际金额（即商家订单创建时的金额）
            String totalAmount = params.get("total_amount");
            int totalAmountInt = new BigDecimal(totalAmount).multiply(new BigDecimal(100)).intValue();
            int totalFee = payOrder.getTotalFee();
            if(totalAmountInt != totalFee) {
                log.error("金额校验失败，订单总金额：{}，返回金额：{}", totalFee, totalAmountInt);
                return result;
            }

            // 3、校验通知中的 seller_id（或者 seller_email）是否为 out_trade_no 这笔单据的对应的操作方
            // （有的时候，一个商家可能有多个 seller_id/seller_email）
            String sellerIdReturn = params.get("seller_id");
            String sellerId = alipay.getSellerId();
            if(!sellerId.equals(sellerIdReturn)) {
                log.error("商家pid校验失败，返回的pid：{}", sellerIdReturn);
                return result;
            }

            // 4、验证 app_id 是否为该商家本身
            String appIdReturn = params.get("app_id");
            String appId = alipay.getAppId();
            if(!appId.equals(appIdReturn)) {
                log.error("商家appid校验失败，返回的appid：{}", appIdReturn);
                return result;
            }

            // 在支付宝的业务通知中，只有交易通知状态为 TRADE_SUCCESS 或 TRADE_FINISHED 时
            // 支付宝才会认定为买家付款成功
            String tradeStatus = params.get("trade_status");
            if(!"TRADE_SUCCESS".equals(tradeStatus) && !"TRADE_FINISHED".equals(tradeStatus)) {
                log.error("支付未成功，订单号：{}", outTradeNo);
                return result;
            }

            // 处理业务，修改订单状态，记录支付日志
            alipayService.processOrder(params);

            // 校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            result = "success";
        } catch (AlipayApiException e) {
            log.error("调用alipay接口出错，code：{}，msg：{}", e.getErrCode(), e.getErrMsg());
        }

        return result;
    }

    /**
     * 用户取消订单
     *
     * @return 响应结果
     */
    @Operation(summary = "用户取消订单")
    @OperateLog(type = OperateType.UPDATE)
    @PostMapping(path = "/trade/close/{orderNo}")
    public ResponseResult<?> tradeClose(@PathVariable String orderNo) {

        log.info("取消订单");
        this.alipayService.cancelOrder(orderNo);

        return ResponseResult.ok().setMessage("订单已取消");
    }

    /**
     * 根据订单号查询订单
     *
     * @param orderNo 订单号
     * @return 响应结果
     */
    @Operation(summary = "根据订单号查询订单")
    @PostMapping(path = "/trade/query/{orderNo}")
    public ResponseResult<?> queryOrder(@PathVariable String orderNo) {

        log.info("查询订单");
        String result = this.alipayService.queryOrder(orderNo);

        return ResponseResult.ok(result).setMessage("查询成功");
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
    @PostMapping(path = "/trade/refund/{orderNo}/{reason}")
    public ResponseResult<?> applyRefund(@PathVariable String orderNo, @PathVariable String reason) {

        log.info("申请退款");
        this.alipayService.refund(orderNo, reason);

        return ResponseResult.ok();
    }

    /**
     * 查询退款
     *
     * @param orderNo 订单号
     * @return 响应结果
     */
    @Operation(summary = "查询退款")
    @GetMapping(path = "/trade/fastpay/refund/{orderNo}")
    public ResponseResult<?> queryRefund(@PathVariable String orderNo) {

        log.info("查询退款");
        String result = this.alipayService.queryRefund(orderNo);

        return ResponseResult.ok(result).setMessage("查询成功");
    }

    /**
     * 获取账单URL
     *
     * @param billDate 账单日期 2024-04-05/2024-04
     * @param type 账单类型（交易账单trade、资金账单signcustomer）
     * @return 响应结果
     */
    @Operation(summary = "获取账单URL")
    @GetMapping(path = "/bill/downloadurl/query/{billDate}/{type}")
    public ResponseResult<String> queryBill(@PathVariable String billDate, @PathVariable String type) {

        log.info("获取账单URL");

        String downloadUrl = this.alipayService.queryBill(billDate, type);

        return ResponseResult.ok(downloadUrl).setMessage("获取账单URL成功");
    }

}