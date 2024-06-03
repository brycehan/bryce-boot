package com.brycehan.boot.pay.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.pay.entity.convert.PayPaymentConvert;
import com.brycehan.boot.pay.entity.dto.PayPaymentPageDto;
import com.brycehan.boot.pay.entity.po.PayPayment;
import com.brycehan.boot.pay.common.PayType;
import com.brycehan.boot.pay.mapper.PayPaymentMapper;
import com.brycehan.boot.pay.service.PayPaymentService;
import com.brycehan.boot.pay.entity.vo.PayPaymentVo;
import com.wechat.pay.java.service.payments.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 支付记录服务实现
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayPaymentServiceImpl extends BaseServiceImpl<PayPaymentMapper, PayPayment> implements PayPaymentService {

    @Override
    public PageResult<PayPaymentVo> page(PayPaymentPageDto payPaymentPageDto) {
        IPage<PayPayment> page = this.baseMapper.selectPage(getPage(payPaymentPageDto), getWrapper(payPaymentPageDto));
        return new PageResult<>(page.getTotal(), PayPaymentConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param payPaymentPageDto 支付记录分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<PayPayment> getWrapper(PayPaymentPageDto payPaymentPageDto){
        LambdaQueryWrapper<PayPayment> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    @Override
    public void export(PayPaymentPageDto payPaymentPageDto) {
        List<PayPayment> payPaymentList = this.baseMapper.selectList(getWrapper(payPaymentPageDto));
        List<PayPaymentVo> payPaymentVoList = PayPaymentConvert.INSTANCE.convert(payPaymentList);
        ExcelUtils.export(PayPaymentVo.class, "支付记录_".concat(DateTimeUtils.today()), "支付记录", payPaymentVoList);
    }

    @Override
    public void createPayment(Transaction transaction) {
        log.info("记录微信支付日志");

        PayPayment payPayment = new PayPayment();
        payPayment.setId(IdGenerator.nextId());
        payPayment.setOrderNo(transaction.getOutTradeNo());
        payPayment.setPaymentType(PayType.WECHAT_PAY.getValue());
        payPayment.setTransactionId(transaction.getTransactionId());
        payPayment.setTradeType(transaction.getTradeType().name());
        payPayment.setTradeState(transaction.getTradeState().name());
        // 用户实际支付金额
        payPayment.setPayerTotal(transaction.getAmount().getPayerTotal());
        payPayment.setContent(JSONUtil.toJsonStr(transaction));

        this.baseMapper.insert(payPayment);
    }

    @Override
    public void createPayment(Map<String, String> params) {
        log.info("记录支付宝支付日志");
        // 获取订单号
        String outTradeNo = params.get("out_trade_no");
        // 业务编号
        String tradeNo = params.get("trade_no");
        // 交易状态
        String tradeStatus = params.get("trade_status");
        // 交易金额
        String totalAmount = params.get("total_amount");
        int totalAmountInt = new BigDecimal(totalAmount).multiply(new BigDecimal(100)).intValue();

        PayPayment payPayment = new PayPayment();
        payPayment.setId(IdGenerator.nextId());
        payPayment.setOrderNo(outTradeNo);
        payPayment.setPaymentType(PayType.ALI_PAY.getValue());
        payPayment.setTransactionId(tradeNo);
        payPayment.setTradeType("电脑网站支付");
        payPayment.setTradeState(tradeStatus);
        // 用户实际支付金额
        payPayment.setPayerTotal(totalAmountInt);
        payPayment.setContent(JSONUtil.toJsonStr(params));

        this.baseMapper.insert(payPayment);
    }

}
