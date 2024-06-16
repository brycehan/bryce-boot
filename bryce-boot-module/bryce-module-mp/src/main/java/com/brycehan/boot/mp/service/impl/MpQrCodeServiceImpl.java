package com.brycehan.boot.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.mp.entity.convert.MpQrCodeConvert;
import com.brycehan.boot.mp.entity.dto.MpQrCodeDto;
import com.brycehan.boot.mp.entity.dto.MpQrCodePageDto;
import com.brycehan.boot.mp.entity.po.MpQrCode;
import com.brycehan.boot.mp.entity.vo.MpQrCodeVo;
import com.brycehan.boot.mp.mapper.MpQrCodeMapper;
import com.brycehan.boot.mp.service.MpQrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 微信公众号带参二维码服务实现
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MpQrCodeServiceImpl extends BaseServiceImpl<MpQrCodeMapper, MpQrCode> implements MpQrCodeService {

    private final WxMpService wxMpService;

    /**
     * 添加微信公众号带参二维码
     *
     * @param mpQrCodeDto 微信公众号带参二维码Dto
     */
    public WxMpQrCodeTicket save(MpQrCodeDto mpQrCodeDto) {
        MpQrCode mpQrCode = MpQrCodeConvert.INSTANCE.convert(mpQrCodeDto);
        WxMpQrCodeTicket ticket;
        try {
            if (mpQrCode.getIsTemporary()) { // 创建临时二维码
                ticket = this.wxMpService.getQrcodeService().qrCodeCreateTmpTicket(mpQrCode.getSceneStr(), mpQrCodeDto.getExpireTimeSeconds());
            } else { // 创建永久二维码
                ticket = this.wxMpService.getQrcodeService().qrCodeCreateLastTicket(mpQrCode.getSceneStr());
            }
        } catch (WxErrorException e) {
            log.error("调用API异常：{}", e.getMessage());
            return null;
        }

        mpQrCode.setId(IdGenerator.nextId());
        mpQrCode.setTicket(ticket.getTicket());
        mpQrCode.setUrl(ticket.getUrl());

        if (mpQrCode.getIsTemporary()) {
            mpQrCode.setExpireTime(LocalDateTime.now().plusSeconds(mpQrCodeDto.getExpireTimeSeconds()));
        }
        this.baseMapper.insert(mpQrCode);

        return ticket;
    }

    @Override
    public PageResult<MpQrCodeVo> page(MpQrCodePageDto mpQrCodePageDto) {
        IPage<MpQrCode> page = this.baseMapper.selectPage(getPage(mpQrCodePageDto), getWrapper(mpQrCodePageDto));
        return new PageResult<>(page.getTotal(), MpQrCodeConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param mpQrCodePageDto 微信公众号带参二维码分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<MpQrCode> getWrapper(MpQrCodePageDto mpQrCodePageDto){
        LambdaQueryWrapper<MpQrCode> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(mpQrCodePageDto.getSceneStr()), MpQrCode::getSceneStr, mpQrCodePageDto.getSceneStr());
        return wrapper;
    }

    @Override
    public void export(MpQrCodePageDto mpQrCodePageDto) {
        List<MpQrCode> mpQrCodeList = this.baseMapper.selectList(getWrapper(mpQrCodePageDto));
        List<MpQrCodeVo> mpQrCodeVoList = MpQrCodeConvert.INSTANCE.convert(mpQrCodeList);
        ExcelUtils.export(MpQrCodeVo.class, "微信公众号带参二维码_".concat(DateTimeUtils.today()), "微信公众号带参二维码", mpQrCodeVoList);
    }

}
