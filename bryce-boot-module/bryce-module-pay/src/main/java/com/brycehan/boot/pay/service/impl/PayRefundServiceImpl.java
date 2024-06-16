package com.brycehan.boot.pay.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.pay.common.AlipayTradeState;
import com.brycehan.boot.pay.common.OrderNoUtils;
import com.brycehan.boot.pay.common.OrderStatus;
import com.brycehan.boot.pay.entity.convert.PayRefundConvert;
import com.brycehan.boot.pay.entity.dto.PayRefundPageDto;
import com.brycehan.boot.pay.entity.po.PayOrder;
import com.brycehan.boot.pay.entity.po.PayRefund;
import com.brycehan.boot.pay.entity.vo.PayRefundVo;
import com.brycehan.boot.pay.mapper.PayRefundMapper;
import com.brycehan.boot.pay.service.PayOrderService;
import com.brycehan.boot.pay.service.PayRefundService;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


/**
 * 退款单服务实现
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Service
@RequiredArgsConstructor
public class PayRefundServiceImpl extends BaseServiceImpl<PayRefundMapper, PayRefund> implements PayRefundService {

    private final PayOrderService payOrderService;

    @Override
    public PageResult<PayRefundVo> page(PayRefundPageDto payRefundPageDto) {
        IPage<PayRefund> page = this.baseMapper.selectPage(getPage(payRefundPageDto), getWrapper(payRefundPageDto));
        return new PageResult<>(page.getTotal(), PayRefundConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param payRefundPageDto 退款单分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<PayRefund> getWrapper(PayRefundPageDto payRefundPageDto){
        LambdaQueryWrapper<PayRefund> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    @Override
    public void export(PayRefundPageDto payRefundPageDto) {
        List<PayRefund> payRefundList = this.baseMapper.selectList(getWrapper(payRefundPageDto));
        List<PayRefundVo> payRefundVoList = PayRefundConvert.INSTANCE.convert(payRefundList);
        ExcelUtils.export(PayRefundVo.class, "退款单_".concat(DateTimeUtils.today()), "退款单", payRefundVoList);
    }

    @Override
    public PayRefund createRefundByOrderNo(String orderNo, String reason) {
        // 根据订单号获取订单信息
        PayOrder payOrder = this.payOrderService.getOrderByOrderNo(orderNo);

        // 暂时只支付全额退款
        // 已经退了款，或不能退款
        if(!OrderStatus.SUCCESS.getType().equals(payOrder.getOrderStatus())) {
            return null;
        }

        // 根据订单号生成退款单
        PayRefund payRefund = new PayRefund();
        payRefund.setId(IdGenerator.nextId());
        payRefund.setOrderNo(orderNo); // 订单号
        payRefund.setRefundNo(OrderNoUtils.getRefundNo()); // 退款单号
        payRefund.setTotalFee(payOrder.getTotalFee()); // 原订单金额（分）
        payRefund.setRefund(payOrder.getTotalFee()); // 退款金额（分）
        payRefund.setReason(reason); // 退款原因

        // 保存退款单
        this.baseMapper.insert(payRefund);

        return payRefund;
    }

    @Override
    public void updateRefund(Refund refund) {
        // 根据商户退款单号修改退款单
        LambdaQueryWrapper<PayRefund> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayRefund::getRefundNo, refund.getOutRefundNo());

        // 设置要修改的字段
        PayRefund payRefund = new PayRefund();
        payRefund.setRefundId(refund.getRefundId());
        payRefund.setRefundStatus(refund.getStatus().name()); // 退款状态
        // 查询退款和申请退款中的返回参数
        payRefund.setContentReturn(JSONUtil.toJsonStr(refund));

        // 更新退款单
        this.baseMapper.update(payRefund, queryWrapper);
    }

    @Override
    public void updateRefund(RefundNotification refundNotification) {
        // 根据商户退款单号修改退款单
        LambdaQueryWrapper<PayRefund> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayRefund::getRefundNo, refundNotification.getOutRefundNo());

        // 设置要修改的字段
        PayRefund payRefund = new PayRefund();
        payRefund.setRefundId(refundNotification.getRefundId());
        payRefund.setRefundStatus(refundNotification.getRefundStatus().name()); // 退款状态
        // 退款通知回调中的参数
        payRefund.setContentNotify(JSONUtil.toJsonStr(refundNotification));

        // 更新退款单
        this.baseMapper.update(payRefund, queryWrapper);
    }

    @Override
    public void updateRefund(String refundNo, String body, AlipayTradeState alipayTradeState) {

        // 根据退款单号修改退款单
        LambdaQueryWrapper<PayRefund> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayRefund::getRefundNo, refundNo);

        HashMap bodyMap = JSONUtil.toBean(body, HashMap.class);
        JSONObject alipayTradeRefundResponse = (JSONObject) bodyMap.get("alipay_trade_refund_response");
        String tradeNo = (String) alipayTradeRefundResponse.get("trade_no"); // 支付宝交易号

        // 设置要修改的字段
        PayRefund payRefund = new PayRefund();
        payRefund.setRefundId(tradeNo);
        payRefund.setRefundStatus(alipayTradeState.getValue());
        payRefund.setContentReturn(body);

        // 更新退款单
        this.baseMapper.update(payRefund, queryWrapper);
    }

}
