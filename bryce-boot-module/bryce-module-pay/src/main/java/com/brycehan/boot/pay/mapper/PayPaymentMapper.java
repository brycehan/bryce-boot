package com.brycehan.boot.pay.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.pay.entity.PayPayment;
import org.apache.ibatis.annotations.Mapper;

/**
* 支付记录Mapper接口
*
* @author Bryce Han
* @since 2024/02/28
*/
@Mapper
public interface PayPaymentMapper extends BryceBaseMapper<PayPayment> {

}
