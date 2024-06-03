package com.brycehan.boot.pay.convert;

import com.brycehan.boot.pay.dto.PayPaymentDto;
import com.brycehan.boot.pay.entity.PayPayment;
import com.brycehan.boot.pay.vo.PayPaymentVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 支付记录转换器
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayPaymentConvert {

    PayPaymentConvert INSTANCE = Mappers.getMapper(PayPaymentConvert.class);

    PayPayment convert(PayPaymentDto payPaymentDto);

    PayPaymentVo convert(PayPayment payPayment);

    List<PayPaymentVo> convert(List<PayPayment> payPaymentList);

}