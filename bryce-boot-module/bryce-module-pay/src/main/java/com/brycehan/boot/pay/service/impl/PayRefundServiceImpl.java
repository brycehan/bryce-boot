package com.brycehan.boot.pay.service.impl;

import com.brycehan.boot.common.util.DateTimeUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.pay.convert.PayRefundConvert;
import com.brycehan.boot.pay.dto.PayRefundPageDto;
import com.brycehan.boot.pay.entity.PayRefund;
import com.brycehan.boot.pay.vo.PayRefundVo;
import com.brycehan.boot.pay.service.PayRefundService;
import com.brycehan.boot.pay.mapper.PayRefundMapper;
import java.util.Objects;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

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
        wrapper.eq(Objects.nonNull(payRefundPageDto.getTenantId()), PayRefund::getTenantId, payRefundPageDto.getTenantId());
        return wrapper;
    }

    @Override
    public void export(PayRefundPageDto payRefundPageDto) {
        List<PayRefund> payRefundList = this.baseMapper.selectList(getWrapper(payRefundPageDto));
        List<PayRefundVo> payRefundVoList = PayRefundConvert.INSTANCE.convert(payRefundList);
        ExcelUtils.export(PayRefundVo.class, "退款单_".concat(DateTimeUtils.today()), "退款单", payRefundVoList);
    }

}
