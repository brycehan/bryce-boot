package com.brycehan.boot.pay.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.pay.convert.PayOrderConvert;
import com.brycehan.boot.pay.dto.PayOrderDto;
import com.brycehan.boot.pay.dto.PayOrderPageDto;
import com.brycehan.boot.pay.entity.PayOrder;
import com.brycehan.boot.pay.vo.PayOrderVo;

/**
 * 订单服务
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
public interface PayOrderService extends BaseService<PayOrder> {

    /**
     * 添加订单
     *
     * @param payOrderDto 订单Dto
     */
    default void save(PayOrderDto payOrderDto) {
        PayOrder payOrder = PayOrderConvert.INSTANCE.convert(payOrderDto);
        payOrder.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(payOrder);
    }

    /**
     * 更新订单
     *
     * @param payOrderDto 订单Dto
     */
    default void update(PayOrderDto payOrderDto) {
        PayOrder payOrder = PayOrderConvert.INSTANCE.convert(payOrderDto);
        this.getBaseMapper().updateById(payOrder);
    }

    /**
     * 订单分页查询
     *
     * @param payOrderPageDto 查询条件
     * @return 分页信息
     */
    PageResult<PayOrderVo> page(PayOrderPageDto payOrderPageDto);

    /**
     * 订单导出数据
     *
     * @param payOrderPageDto 订单查询条件
     */
    void export(PayOrderPageDto payOrderPageDto);

}
