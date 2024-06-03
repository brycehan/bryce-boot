package com.brycehan.boot.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.mp.entity.convert.MpMessageTemplateConvert;
import com.brycehan.boot.mp.entity.dto.MpMessageTemplatePageDto;
import com.brycehan.boot.mp.entity.po.MpMessageTemplate;
import com.brycehan.boot.mp.mapper.MpMessageTemplateMapper;
import com.brycehan.boot.mp.service.MpMessageTemplateService;
import com.brycehan.boot.mp.entity.vo.MpMessageTemplateVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;


/**
 * 微信公众号消息模板服务实现
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MpMessageTemplateServiceImpl extends BaseServiceImpl<MpMessageTemplateMapper, MpMessageTemplate> implements MpMessageTemplateService {

    private final WxMpService wxMpService;

    @Override
    public PageResult<MpMessageTemplateVo> page(MpMessageTemplatePageDto mpMessageTemplatePageDto) {
        IPage<MpMessageTemplate> page = this.baseMapper.selectPage(getPage(mpMessageTemplatePageDto), getWrapper(mpMessageTemplatePageDto));
        return new PageResult<>(page.getTotal(), MpMessageTemplateConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param mpMessageTemplatePageDto 微信公众号消息模板分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<MpMessageTemplate> getWrapper(MpMessageTemplatePageDto mpMessageTemplatePageDto){
        LambdaQueryWrapper<MpMessageTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(mpMessageTemplatePageDto.getStatus()), MpMessageTemplate::getStatus, mpMessageTemplatePageDto.getStatus());
        wrapper.like(StringUtils.isNotEmpty(mpMessageTemplatePageDto.getName()), MpMessageTemplate::getName, mpMessageTemplatePageDto.getName());
        return wrapper;
    }

    @Override
    public void export(MpMessageTemplatePageDto mpMessageTemplatePageDto) {
        List<MpMessageTemplate> mpMessageTemplateList = this.baseMapper.selectList(getWrapper(mpMessageTemplatePageDto));
        List<MpMessageTemplateVo> mpMessageTemplateVoList = MpMessageTemplateConvert.INSTANCE.convert(mpMessageTemplateList);
        ExcelUtils.export(MpMessageTemplateVo.class, "微信公众号消息模板_".concat(DateTimeUtils.today()), "微信公众号消息模板", mpMessageTemplateVoList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncMpTemplate() {
        List<WxMpTemplate> allPrivateTemplate;
        try {
            allPrivateTemplate = this.wxMpService.getTemplateMsgService().getAllPrivateTemplate();
        } catch (WxErrorException e) {
            log.error("调用API出错，{}", e.getMessage());
            return;
        }

        List<MpMessageTemplate> templates = allPrivateTemplate.stream()
                .map(MpMessageTemplate::create).toList();
        List<String> templateIds = templates.stream()
                .map(MpMessageTemplate::getTemplateId).toList();

        if (!CollectionUtils.isEmpty(templateIds)) {
            LambdaQueryWrapper<MpMessageTemplate> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MpMessageTemplate::getTemplateId, templateIds);
            this.remove(queryWrapper);

            this.saveBatch(templates);
            return;
        }
        log.debug("没有模板");
    }

}
