package com.brycehan.boot.pay.common.task;

import com.alipay.api.AlipayClient;
import com.brycehan.boot.pay.entity.po.PayOrder;
import com.brycehan.boot.pay.common.PayType;
import com.brycehan.boot.pay.service.AlipayService;
import com.brycehan.boot.pay.service.PayOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 支付宝支付定时任务
 *
 * @author Bryce Han
 * @since 2024/3/8
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
@ConditionalOnBean(value = AlipayClient.class)
public class AlipayTask {

    private final AlipayService alipayService;

    private final PayOrderService payOrderService;

    /**
     * 从第0秒开始每分钟执行1次，查询创建超过1分钟并且未支付订单<br>
     * 已支付的，处理为本地已支付<br>
     * 未支付的，暂不处理
     */
    @Scheduled(cron = "0 * * * * ?")
    public void orderConfirm() {

        log.info("orderConfirm，被执行......");

        List<PayOrder> payOrderList = this.payOrderService.getNoPayOrderByDuration(1, PayType.ALI_PAY);

        for (PayOrder payOrder : payOrderList) {
            String orderNo = payOrder.getOrderNo();
            log.warn("orderConfirm，检查超时订单，订单号：{}", orderNo);

            // 核实订单状态：调用支付宝查单接口
            this.alipayService.checkOrderStatus(orderNo, false);
        }
    }

    /**
     * 从第0秒开始每分钟执行1次，查询创建超过2小时并且未支付订单<br>
     * 已支付的，处理为本地已支付
     * 未支付的，自动取消
     */
    @Scheduled(cron = "0 * * * * ?")
    public void orderCancel() {
        log.info("orderCancel 被执行......");

        List<PayOrder> payOrderList = this.payOrderService.getNoPayOrderByDuration(120, PayType.ALI_PAY);

        for (PayOrder payOrder : payOrderList) {
            String orderNo = payOrder.getOrderNo();
            log.warn("orderCancel检查超时订单，订单号：{}", orderNo);

            // 核实订单状态：调用支付宝支付查单接口
            this.alipayService.checkOrderStatus(orderNo, true);
        }
    }
}
