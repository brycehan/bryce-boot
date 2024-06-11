package com.brycehan.boot.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.mp.entity.convert.MpTemplateMessageLogConvert;
import com.brycehan.boot.mp.entity.dto.MpTemplateMessageLogPageDto;
import com.brycehan.boot.mp.entity.po.MpTemplateMessageLog;
import com.brycehan.boot.mp.entity.vo.MpTemplateMessageLogVo;
import com.brycehan.boot.mp.mapper.MpTemplateMessageLogMapper;
import com.brycehan.boot.mp.service.MpTemplateMessageLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 微信公众号模版消息发送记录服务实现
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Service
@RequiredArgsConstructor
public class MpTemplateMessageLogServiceImpl extends BaseServiceImpl<MpTemplateMessageLogMapper, MpTemplateMessageLog> implements MpTemplateMessageLogService {

    @Override
    public PageResult<MpTemplateMessageLogVo> page(MpTemplateMessageLogPageDto mpTemplateMessageLogPageDto) {
        IPage<MpTemplateMessageLog> page = this.baseMapper.selectPage(getPage(mpTemplateMessageLogPageDto), getWrapper(mpTemplateMessageLogPageDto));
        return new PageResult<>(page.getTotal(), MpTemplateMessageLogConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param mpTemplateMessageLogPageDto 微信公众号模版消息发送记录分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<MpTemplateMessageLog> getWrapper(MpTemplateMessageLogPageDto mpTemplateMessageLogPageDto){
        LambdaQueryWrapper<MpTemplateMessageLog> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    @Override
    public void export(MpTemplateMessageLogPageDto mpTemplateMessageLogPageDto) {
        List<MpTemplateMessageLog> mpTemplateMessageLogList = this.baseMapper.selectList(getWrapper(mpTemplateMessageLogPageDto));
        List<MpTemplateMessageLogVo> mpTemplateMessageLogVoList = MpTemplateMessageLogConvert.INSTANCE.convert(mpTemplateMessageLogList);
        ExcelUtils.export(MpTemplateMessageLogVo.class, "微信公众号模版消息发送记录_".concat(DateTimeUtils.today()), "微信公众号模版消息发送记录", mpTemplateMessageLogVoList);
    }

}
