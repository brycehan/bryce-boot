package com.brycehan.boot.pay.service.impl;

import com.brycehan.boot.common.util.DateTimeUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.pay.convert.PayPaymentConvert;
import com.brycehan.boot.pay.dto.PayPaymentPageDto;
import com.brycehan.boot.pay.entity.PayPayment;
import com.brycehan.boot.pay.vo.PayPaymentVo;
import com.brycehan.boot.pay.service.PayPaymentService;
import com.brycehan.boot.pay.mapper.PayPaymentMapper;
import java.util.Objects;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * 支付记录服务实现
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
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
        wrapper.eq(Objects.nonNull(payPaymentPageDto.getTenantId()), PayPayment::getTenantId, payPaymentPageDto.getTenantId());
        return wrapper;
    }

    @Override
    public void export(PayPaymentPageDto payPaymentPageDto) {
        List<PayPayment> payPaymentList = this.baseMapper.selectList(getWrapper(payPaymentPageDto));
        List<PayPaymentVo> payPaymentVoList = PayPaymentConvert.INSTANCE.convert(payPaymentList);
        ExcelUtils.export(PayPaymentVo.class, "支付记录_".concat(DateTimeUtils.today()), "支付记录", payPaymentVoList);
    }

}
