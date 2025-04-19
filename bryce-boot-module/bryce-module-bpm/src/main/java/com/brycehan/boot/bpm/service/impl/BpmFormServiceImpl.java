package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.bpm.entity.convert.BpmFormConvert;
import com.brycehan.boot.bpm.entity.dto.BpmFormDto;
import com.brycehan.boot.bpm.entity.dto.BpmFormPageDto;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.entity.vo.BpmFormVo;
import com.brycehan.boot.bpm.mapper.BpmFormMapper;
import com.brycehan.boot.bpm.service.BpmFormService;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 表单定义服务实现
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmFormServiceImpl extends BaseServiceImpl<BpmFormMapper, BpmForm> implements BpmFormService {

    /**
     * 添加表单定义
     *
     * @param bpmFormDto 表单定义Dto
     */
    public void save(BpmFormDto bpmFormDto) {
        // 校验表单字段是否重复
        // ...
        BpmForm bpmForm = BpmFormConvert.INSTANCE.convert(bpmFormDto);
        bpmForm.setId(IdGenerator.nextId());
        baseMapper.insert(bpmForm);
    }

    /**
     * 更新表单定义
     *
     * @param bpmFormDto 表单定义Dto
     */
    public void update(BpmFormDto bpmFormDto) {
        BpmForm bpmForm = BpmFormConvert.INSTANCE.convert(bpmFormDto);
        baseMapper.updateById(bpmForm);
    }

    @Override
    public PageResult<BpmFormVo> page(BpmFormPageDto bpmFormPageDto) {
        IPage<BpmForm> page = baseMapper.selectPage(bpmFormPageDto.toPage(), getWrapper(bpmFormPageDto));
        return PageResult.of(BpmFormConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    @Override
    public List<BpmFormVo> list(BpmFormPageDto bpmFormPageDto) {
        List<BpmForm> bpmFormList = baseMapper.selectList(getWrapper(bpmFormPageDto));
        return BpmFormConvert.INSTANCE.convert(bpmFormList);
    }

    /**
     * 封装查询条件
     *
     * @param bpmFormPageDto 表单定义分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<BpmForm> getWrapper(BpmFormPageDto bpmFormPageDto){
        LambdaQueryWrapper<BpmForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(bpmFormPageDto.getStatus()), BpmForm::getStatus, bpmFormPageDto.getStatus());
        wrapper.eq(Objects.nonNull(bpmFormPageDto.getTenantId()), BpmForm::getTenantId, bpmFormPageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(bpmFormPageDto.getName()), BpmForm::getName, bpmFormPageDto.getName());
        return wrapper;
    }

    @Override
    public void export(BpmFormPageDto bpmFormPageDto) {
        List<BpmForm> bpmFormList = baseMapper.selectList(getWrapper(bpmFormPageDto));
        List<BpmFormVo> bpmFormVoList = BpmFormConvert.INSTANCE.convert(bpmFormList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(BpmFormVo.class, "表单定义_".concat(today), "表单定义", bpmFormVoList);
    }

    @Override
    public Map<Long, String> getFormNameMap(List<Long> formIds) {
        if (CollUtil.isEmpty(CollUtil.newHashSet(formIds))) {
            return Map.of();
        }

        LambdaQueryWrapper<BpmForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(BpmForm::getId, BpmForm::getName);
        queryWrapper.in(BpmForm::getId, formIds);

        List<BpmForm> bpmFormList = baseMapper.selectList(queryWrapper);

        return bpmFormList.stream().collect(Collectors.toMap(BpmForm::getId, BpmForm::getName));
    }

    @Override
    public Map<Long, BpmForm> getFormMap(List<Long> formIds) {
        if (CollUtil.isEmpty(CollUtil.newHashSet(formIds))) {
            return Map.of();
        }

        LambdaQueryWrapper<BpmForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BpmForm::getId, formIds);

        List<BpmForm> bpmFormList = baseMapper.selectList(queryWrapper);

        return bpmFormList.stream().collect(Collectors.toMap(BpmForm::getId, Function.identity()));
    }

}
