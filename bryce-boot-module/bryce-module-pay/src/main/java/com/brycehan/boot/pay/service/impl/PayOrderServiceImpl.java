package com.brycehan.boot.pay.service.impl;

import com.brycehan.boot.common.util.DateTimeUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.pay.convert.PayOrderConvert;
import com.brycehan.boot.pay.dto.PayOrderPageDto;
import com.brycehan.boot.pay.entity.PayOrder;
import com.brycehan.boot.pay.vo.PayOrderVo;
import com.brycehan.boot.pay.service.PayOrderService;
import com.brycehan.boot.pay.mapper.PayOrderMapper;
import java.util.Objects;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * 订单服务实现
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Service
@RequiredArgsConstructor
public class PayOrderServiceImpl extends BaseServiceImpl<PayOrderMapper, PayOrder> implements PayOrderService {

    @Override
    public PageResult<PayOrderVo> page(PayOrderPageDto payOrderPageDto) {

        IPage<PayOrder> page = this.baseMapper.selectPage(getPage(payOrderPageDto), getWrapper(payOrderPageDto));

        return new PageResult<>(page.getTotal(), PayOrderConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param payOrderPageDto 订单分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<PayOrder> getWrapper(PayOrderPageDto payOrderPageDto){
        LambdaQueryWrapper<PayOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(payOrderPageDto.getTenantId()), PayOrder::getTenantId, payOrderPageDto.getTenantId());
        return wrapper;
    }

    @Override
    public void export(PayOrderPageDto payOrderPageDto) {
        List<PayOrder> payOrderList = this.baseMapper.selectList(getWrapper(payOrderPageDto));
        List<PayOrderVo> payOrderVoList = PayOrderConvert.INSTANCE.convert(payOrderList);
        ExcelUtils.export(PayOrderVo.class, "订单_".concat(DateTimeUtils.today()), "订单", payOrderVoList);
    }

}
