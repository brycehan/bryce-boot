package com.brycehan.boot.pay.convert;

import com.brycehan.boot.pay.dto.PayOrderDto;
import com.brycehan.boot.pay.entity.PayOrder;
import com.brycehan.boot.pay.vo.PayOrderVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 订单转换器
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Mapper
public interface PayOrderConvert {

    PayOrderConvert INSTANCE = Mappers.getMapper(PayOrderConvert.class);

    PayOrder convert(PayOrderDto payOrderDto);

    PayOrderVo convert(PayOrder payOrder);

    List<PayOrderVo> convert(List<PayOrder> payOrderList);

}