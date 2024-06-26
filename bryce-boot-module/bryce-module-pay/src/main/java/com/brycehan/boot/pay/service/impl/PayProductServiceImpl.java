package com.brycehan.boot.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.pay.entity.convert.PayProductConvert;
import com.brycehan.boot.pay.entity.dto.PayProductPageDto;
import com.brycehan.boot.pay.entity.po.PayProduct;
import com.brycehan.boot.pay.entity.vo.PayProductVo;
import com.brycehan.boot.pay.mapper.PayProductMapper;
import com.brycehan.boot.pay.service.PayProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 商品服务实现
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Service
@RequiredArgsConstructor
public class PayProductServiceImpl extends BaseServiceImpl<PayProductMapper, PayProduct> implements PayProductService {

    @Override
    public PageResult<PayProductVo> page(PayProductPageDto payProductPageDto) {
        IPage<PayProduct> page = this.baseMapper.selectPage(getPage(payProductPageDto), getWrapper(payProductPageDto));
        return new PageResult<>(page.getTotal(), PayProductConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param payProductPageDto 商品分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<PayProduct> getWrapper(PayProductPageDto payProductPageDto){
        LambdaQueryWrapper<PayProduct> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    @Override
    public void export(PayProductPageDto payProductPageDto) {
        List<PayProduct> payProductList = this.baseMapper.selectList(getWrapper(payProductPageDto));
        List<PayProductVo> payProductVoList = PayProductConvert.INSTANCE.convert(payProductList);
        ExcelUtils.export(PayProductVo.class, "商品_".concat(DateTimeUtils.today()), "商品", payProductVoList);
    }

}
