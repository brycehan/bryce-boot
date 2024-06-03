package com.brycehan.boot.pay.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.pay.entity.po.PayRefund;
import org.apache.ibatis.annotations.Mapper;

/**
* 退款单Mapper接口
*
* @author Bryce Han
* @since 2024/02/28
*/
@Mapper
public interface PayRefundMapper extends BryceBaseMapper<PayRefund> {

}
