package com.brycehan.boot.pay.convert;

import com.brycehan.boot.pay.dto.PayRefundDto;
import com.brycehan.boot.pay.entity.PayRefund;
import com.brycehan.boot.pay.vo.PayRefundVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 退款单转换器
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Mapper
public interface PayRefundConvert {

    PayRefundConvert INSTANCE = Mappers.getMapper(PayRefundConvert.class);

    PayRefund convert(PayRefundDto payRefundDto);

    PayRefundVo convert(PayRefund payRefund);

    List<PayRefundVo> convert(List<PayRefund> payRefundList);

}