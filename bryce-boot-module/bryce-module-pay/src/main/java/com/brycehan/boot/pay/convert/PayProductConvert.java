package com.brycehan.boot.pay.convert;

import com.brycehan.boot.pay.dto.PayProductDto;
import com.brycehan.boot.pay.entity.PayProduct;
import com.brycehan.boot.pay.vo.PayProductVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品转换器
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayProductConvert {

    PayProductConvert INSTANCE = Mappers.getMapper(PayProductConvert.class);

    PayProduct convert(PayProductDto payProductDto);

    PayProductVo convert(PayProduct payProduct);

    List<PayProductVo> convert(List<PayProduct> payProductList);

}