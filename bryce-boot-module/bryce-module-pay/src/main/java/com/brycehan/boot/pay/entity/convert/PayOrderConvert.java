package com.brycehan.boot.pay.entity.convert;

import com.brycehan.boot.pay.entity.dto.PayOrderDto;
import com.brycehan.boot.pay.entity.po.PayOrder;
import com.brycehan.boot.pay.entity.vo.PayOrderVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 订单转换器
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayOrderConvert {

    PayOrderConvert INSTANCE = Mappers.getMapper(PayOrderConvert.class);

    PayOrder convert(PayOrderDto payOrderDto);

    PayOrderVo convert(PayOrder payOrder);

    List<PayOrderVo> convert(List<PayOrder> payOrderList);

}