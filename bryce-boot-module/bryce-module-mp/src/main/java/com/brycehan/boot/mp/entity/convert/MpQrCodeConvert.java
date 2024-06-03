package com.brycehan.boot.mp.entity.convert;

import com.brycehan.boot.mp.entity.dto.MpQrCodeDto;
import com.brycehan.boot.mp.entity.po.MpQrCode;
import com.brycehan.boot.mp.entity.vo.MpQrCodeVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 微信公众号带参二维码转换器
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MpQrCodeConvert {

    MpQrCodeConvert INSTANCE = Mappers.getMapper(MpQrCodeConvert.class);

    MpQrCode convert(MpQrCodeDto mpQrCodeDto);

    MpQrCodeVo convert(MpQrCode mpQrCode);

    List<MpQrCodeVo> convert(List<MpQrCode> mpQrCodeList);

}