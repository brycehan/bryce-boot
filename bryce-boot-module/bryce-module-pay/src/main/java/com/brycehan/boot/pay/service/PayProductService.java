package com.brycehan.boot.pay.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.pay.entity.convert.PayProductConvert;
import com.brycehan.boot.pay.entity.dto.PayProductDto;
import com.brycehan.boot.pay.entity.dto.PayProductPageDto;
import com.brycehan.boot.pay.entity.po.PayProduct;
import com.brycehan.boot.pay.entity.vo.PayProductVo;

/**
 * 商品服务
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
public interface PayProductService extends BaseService<PayProduct> {

    /**
     * 添加商品
     *
     * @param payProductDto 商品Dto
     */
    default void save(PayProductDto payProductDto) {
        PayProduct payProduct = PayProductConvert.INSTANCE.convert(payProductDto);
        payProduct.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(payProduct);
    }

    /**
     * 更新商品
     *
     * @param payProductDto 商品Dto
     */
    default void update(PayProductDto payProductDto) {
        PayProduct payProduct = PayProductConvert.INSTANCE.convert(payProductDto);
        this.getBaseMapper().updateById(payProduct);
    }

    /**
     * 商品分页查询
     *
     * @param payProductPageDto 查询条件
     * @return 分页信息
     */
    PageResult<PayProductVo> page(PayProductPageDto payProductPageDto);

    /**
     * 商品导出数据
     *
     * @param payProductPageDto 商品查询条件
     */
    void export(PayProductPageDto payProductPageDto);

}
