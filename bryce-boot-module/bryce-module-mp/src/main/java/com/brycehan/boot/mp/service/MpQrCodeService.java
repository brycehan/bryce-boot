package com.brycehan.boot.mp.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.mp.convert.MpQrCodeConvert;
import com.brycehan.boot.mp.dto.MpQrCodeDto;
import com.brycehan.boot.mp.dto.MpQrCodePageDto;
import com.brycehan.boot.mp.entity.MpQrCode;
import com.brycehan.boot.mp.vo.MpQrCodeVo;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 * 微信公众号带参二维码服务
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
public interface MpQrCodeService extends BaseService<MpQrCode> {

    /**
     * 添加微信公众号带参二维码
     *
     * @param mpQrCodeDto 微信公众号带参二维码Dto
     */
    WxMpQrCodeTicket save(MpQrCodeDto mpQrCodeDto);

    /**
     * 更新微信公众号带参二维码
     *
     * @param mpQrCodeDto 微信公众号带参二维码Dto
     */
    default void update(MpQrCodeDto mpQrCodeDto) {
        MpQrCode mpQrCode = MpQrCodeConvert.INSTANCE.convert(mpQrCodeDto);
        this.getBaseMapper().updateById(mpQrCode);
    }

    /**
     * 微信公众号带参二维码分页查询
     *
     * @param mpQrCodePageDto 查询条件
     * @return 分页信息
     */
    PageResult<MpQrCodeVo> page(MpQrCodePageDto mpQrCodePageDto);

    /**
     * 微信公众号带参二维码导出数据
     *
     * @param mpQrCodePageDto 微信公众号带参二维码查询条件
     */
    void export(MpQrCodePageDto mpQrCodePageDto);

}
