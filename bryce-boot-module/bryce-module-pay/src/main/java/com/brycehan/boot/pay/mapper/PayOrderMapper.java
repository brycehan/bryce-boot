package com.brycehan.boot.pay.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.pay.entity.PayOrder;

/**
* 订单Mapper接口
*
* @author Bryce Han
* @since 2024/02/27
*/
@Mapper
public interface PayOrderMapper extends BryceBaseMapper<PayOrder> {

}
