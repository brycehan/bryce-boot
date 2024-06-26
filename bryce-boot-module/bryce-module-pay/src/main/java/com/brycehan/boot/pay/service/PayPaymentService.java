package com.brycehan.boot.pay.service;

import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.pay.entity.convert.PayPaymentConvert;
import com.brycehan.boot.pay.entity.dto.PayPaymentDto;
import com.brycehan.boot.pay.entity.dto.PayPaymentPageDto;
import com.brycehan.boot.pay.entity.po.PayPayment;
import com.brycehan.boot.pay.entity.vo.PayPaymentVo;
import com.wechat.pay.java.service.payments.model.Transaction;

import java.util.Map;

/**
 * 支付记录服务
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
public interface PayPaymentService extends BaseService<PayPayment> {

    /**
     * 添加支付记录
     *
     * @param payPaymentDto 支付记录Dto
     */
    default void save(PayPaymentDto payPaymentDto) {
        PayPayment payPayment = PayPaymentConvert.INSTANCE.convert(payPaymentDto);
        payPayment.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(payPayment);
    }

    /**
     * 更新支付记录
     *
     * @param payPaymentDto 支付记录Dto
     */
    default void update(PayPaymentDto payPaymentDto) {
        PayPayment payPayment = PayPaymentConvert.INSTANCE.convert(payPaymentDto);
        this.getBaseMapper().updateById(payPayment);
    }

    /**
     * 支付记录分页查询
     *
     * @param payPaymentPageDto 查询条件
     * @return 分页信息
     */
    PageResult<PayPaymentVo> page(PayPaymentPageDto payPaymentPageDto);

    /**
     * 支付记录导出数据
     *
     * @param payPaymentPageDto 支付记录查询条件
     */
    void export(PayPaymentPageDto payPaymentPageDto);

    /**
     * 记录支付日志——微信
     *
     * @param transaction 支付参数
     */
    void createPayment(Transaction transaction);

    /**
     * 记录支付日志——支付宝
     * @param params 支付参数
     */
    void createPayment(Map<String, String> params);
}
