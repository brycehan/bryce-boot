package com.brycehan.boot.pay.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.pay.entity.convert.PayRefundConvert;
import com.brycehan.boot.pay.entity.dto.PayRefundDto;
import com.brycehan.boot.pay.entity.dto.PayRefundPageDto;
import com.brycehan.boot.pay.entity.po.PayRefund;
import com.brycehan.boot.pay.common.AlipayTradeState;
import com.brycehan.boot.pay.entity.vo.PayRefundVo;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;

/**
 * 退款单服务
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
public interface PayRefundService extends BaseService<PayRefund> {

    /**
     * 添加退款单
     *
     * @param payRefundDto 退款单Dto
     */
    default void save(PayRefundDto payRefundDto) {
        PayRefund payRefund = PayRefundConvert.INSTANCE.convert(payRefundDto);
        payRefund.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(payRefund);
    }

    /**
     * 更新退款单
     *
     * @param payRefundDto 退款单Dto
     */
    default void update(PayRefundDto payRefundDto) {
        PayRefund payRefund = PayRefundConvert.INSTANCE.convert(payRefundDto);
        this.getBaseMapper().updateById(payRefund);
    }

    /**
     * 退款单分页查询
     *
     * @param payRefundPageDto 查询条件
     * @return 分页信息
     */
    PageResult<PayRefundVo> page(PayRefundPageDto payRefundPageDto);

    /**
     * 退款单导出数据
     *
     * @param payRefundPageDto 退款单查询条件
     */
    void export(PayRefundPageDto payRefundPageDto);

    /**
     * 根据订单号创建退款订单
     *
     * @param orderNo 订单号
     * @param reason 退款原因
     * @return 退款单
     */
    PayRefund createRefundByOrderNo(String orderNo, String reason);

    /**
     * 记录退款记录
     *
     * @param refund 微信退款返回参数
     */
    void updateRefund(Refund refund);

    /**
     * 记录退款记录
     *
     * @param refundNotification 微信退款通知返回参数
     */
    void updateRefund(RefundNotification refundNotification);

    /**
     * 支付宝更新退款单
     *
     * @param refundNo 退款单号
     * @param body 响应体
     * @param alipayTradeState 交易状态
     */
    void updateRefund(String refundNo, String body, AlipayTradeState alipayTradeState);

}
