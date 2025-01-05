package com.brycehan.boot.system.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysAttachmentConvert;
import com.brycehan.boot.system.entity.dto.SysAttachmentDto;
import com.brycehan.boot.system.entity.dto.SysAttachmentPageDto;
import com.brycehan.boot.system.entity.po.SysAttachment;
import com.brycehan.boot.system.entity.vo.SysAttachmentVo;
import com.brycehan.boot.system.mapper.SysAttachmentMapper;
import com.brycehan.boot.system.service.SysAttachmentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 系统附件服务实现
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachmentMapper, SysAttachment> implements SysAttachmentService {

    /**
     * 添加系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     */
    public void save(SysAttachmentDto sysAttachmentDto) {
        SysAttachment sysAttachment = SysAttachmentConvert.INSTANCE.convert(sysAttachmentDto);
        sysAttachment.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysAttachment);
    }

    /**
     * 更新系统附件
     *
     * @param sysAttachmentDto 系统附件Dto
     */
    public void update(SysAttachmentDto sysAttachmentDto) {
        SysAttachment sysAttachment = SysAttachmentConvert.INSTANCE.convert(sysAttachmentDto);
        this.baseMapper.updateById(sysAttachment);
    }

    @Override
    public PageResult<SysAttachmentVo> page(SysAttachmentPageDto sysAttachmentPageDto) {
        IPage<SysAttachment> page = this.baseMapper.selectPage(sysAttachmentPageDto.toPage(), getWrapper(sysAttachmentPageDto));
        return new PageResult<>(page.getTotal(), SysAttachmentConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysAttachmentPageDto 系统附件分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysAttachment> getWrapper(SysAttachmentPageDto sysAttachmentPageDto) {
        LambdaQueryWrapper<SysAttachment> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(sysAttachmentPageDto.getName()), SysAttachment::getName, sysAttachmentPageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysAttachmentPageDto.getPlatform()), SysAttachment::getPlatform, sysAttachmentPageDto.getPlatform());

        return wrapper;
    }

    @Override
    public void export(SysAttachmentPageDto sysAttachmentPageDto) {
        List<SysAttachment> sysAttachmentList = this.baseMapper.selectList(getWrapper(sysAttachmentPageDto));
        List<SysAttachmentVo> sysAttachmentVoList = SysAttachmentConvert.INSTANCE.convert(sysAttachmentList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysAttachmentVo.class, "系统附件_".concat(today), "系统附件", sysAttachmentVoList);
    }

}
