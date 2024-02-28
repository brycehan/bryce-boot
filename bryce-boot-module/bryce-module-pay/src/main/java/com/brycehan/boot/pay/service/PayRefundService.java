package com.brycehan.boot.pay.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.pay.convert.PayRefundConvert;
import com.brycehan.boot.pay.dto.PayRefundDto;
import com.brycehan.boot.pay.dto.PayRefundPageDto;
import com.brycehan.boot.pay.entity.PayRefund;
import com.brycehan.boot.pay.vo.PayRefundVo;

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

}
