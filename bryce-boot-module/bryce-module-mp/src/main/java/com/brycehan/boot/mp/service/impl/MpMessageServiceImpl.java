package com.brycehan.boot.mp.service.impl;

import com.brycehan.boot.common.util.DateTimeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.mp.convert.MpMessageConvert;
import com.brycehan.boot.mp.dto.MpMessagePageDto;
import com.brycehan.boot.mp.entity.MpMessage;
import com.brycehan.boot.mp.vo.MpMessageVo;
import com.brycehan.boot.mp.service.MpMessageService;
import com.brycehan.boot.mp.mapper.MpMessageMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * 微信公众号消息服务实现
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Service
@RequiredArgsConstructor
public class MpMessageServiceImpl extends BaseServiceImpl<MpMessageMapper, MpMessage> implements MpMessageService {

    @Override
    public PageResult<MpMessageVo> page(MpMessagePageDto mpMessagePageDto) {
        IPage<MpMessage> page = this.baseMapper.selectPage(getPage(mpMessagePageDto), getWrapper(mpMessagePageDto));
        return new PageResult<>(page.getTotal(), MpMessageConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param mpMessagePageDto 微信公众号消息分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<MpMessage> getWrapper(MpMessagePageDto mpMessagePageDto){
        LambdaQueryWrapper<MpMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(mpMessagePageDto.getMessageType()), MpMessage::getMessageType, mpMessagePageDto.getMessageType());
        return wrapper;
    }

    @Override
    public void export(MpMessagePageDto mpMessagePageDto) {
        List<MpMessage> mpMessageList = this.baseMapper.selectList(getWrapper(mpMessagePageDto));
        List<MpMessageVo> mpMessageVoList = MpMessageConvert.INSTANCE.convert(mpMessageList);
        ExcelUtils.export(MpMessageVo.class, "微信公众号消息_".concat(DateTimeUtils.today()), "微信公众号消息", mpMessageVoList);
    }

    /**
     * 记录消息，异步入库
     *
     * @param mpMessage 消息
     */
    @Async
    @Override
    public void addMpMessage(MpMessage mpMessage) {
        this.baseMapper.insert(mpMessage);
    }

}
