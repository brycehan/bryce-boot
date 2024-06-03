package com.brycehan.boot.pay.entity.convert;

import com.brycehan.boot.pay.entity.dto.PayRefundDto;
import com.brycehan.boot.pay.entity.po.PayRefund;
import com.brycehan.boot.pay.entity.vo.PayRefundVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 退款单转换器
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayRefundConvert {

    PayRefundConvert INSTANCE = Mappers.getMapper(PayRefundConvert.class);

    PayRefund convert(PayRefundDto payRefundDto);

    PayRefundVo convert(PayRefund payRefund);

    List<PayRefundVo> convert(List<PayRefund> payRefundList);

}