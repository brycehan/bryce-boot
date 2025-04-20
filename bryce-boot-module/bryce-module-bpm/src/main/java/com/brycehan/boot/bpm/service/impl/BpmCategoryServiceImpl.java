package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.bpm.entity.convert.BpmCategoryConvert;
import com.brycehan.boot.bpm.entity.dto.BpmCategoryDto;
import com.brycehan.boot.bpm.entity.dto.BpmCategoryPageDto;
import com.brycehan.boot.bpm.entity.po.BpmCategory;
import com.brycehan.boot.bpm.entity.vo.BpmCategoryVo;
import com.brycehan.boot.bpm.mapper.BpmCategoryMapper;
import com.brycehan.boot.bpm.service.BpmCategoryService;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 流程分类服务实现
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmCategoryServiceImpl extends BaseServiceImpl<BpmCategoryMapper, BpmCategory> implements BpmCategoryService {

    /**
     * 添加流程分类
     *
     * @param bpmCategoryDto 流程分类Dto
     */
    public void save(BpmCategoryDto bpmCategoryDto) {
        BpmCategory bpmCategory = BpmCategoryConvert.INSTANCE.convert(bpmCategoryDto);
        bpmCategory.setId(IdGenerator.nextId());
        baseMapper.insert(bpmCategory);
    }

    /**
     * 更新流程分类
     *
     * @param bpmCategoryDto 流程分类Dto
     */
    public void update(BpmCategoryDto bpmCategoryDto) {
        BpmCategory bpmCategory = BpmCategoryConvert.INSTANCE.convert(bpmCategoryDto);
        baseMapper.updateById(bpmCategory);
    }

    @Override
    public PageResult<BpmCategoryVo> page(BpmCategoryPageDto bpmCategoryPageDto) {
        IPage<BpmCategory> page = baseMapper.selectPage(bpmCategoryPageDto.toPage(), getWrapper(bpmCategoryPageDto));
        return PageResult.of(BpmCategoryConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    @Override
    public List<BpmCategoryVo> list(BpmCategoryPageDto bpmCategoryPageDto) {
        List<BpmCategory> bpmCategoryList = baseMapper.selectList(getWrapper(bpmCategoryPageDto));
        return BpmCategoryConvert.INSTANCE.convert(bpmCategoryList);
    }

    /**
     * 封装查询条件
     *
     * @param bpmCategoryPageDto 流程分类分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<BpmCategory> getWrapper(BpmCategoryPageDto bpmCategoryPageDto){
        LambdaQueryWrapper<BpmCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(bpmCategoryPageDto.getStatus() != null, BpmCategory::getStatus, bpmCategoryPageDto.getStatus());
        wrapper.eq(Objects.nonNull(bpmCategoryPageDto.getTenantId()), BpmCategory::getTenantId, bpmCategoryPageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(bpmCategoryPageDto.getName()), BpmCategory::getName, bpmCategoryPageDto.getName());
        return wrapper;
    }

    @Override
    public void export(BpmCategoryPageDto bpmCategoryPageDto) {
        List<BpmCategory> bpmCategoryList = baseMapper.selectList(getWrapper(bpmCategoryPageDto));
        List<BpmCategoryVo> bpmCategoryVoList = BpmCategoryConvert.INSTANCE.convert(bpmCategoryList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(BpmCategoryVo.class, "流程分类_".concat(today), "流程分类", bpmCategoryVoList);
    }

    @Override
    public String getCategoryName(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        LambdaQueryWrapper<BpmCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(BpmCategory::getName);
        queryWrapper.eq(BpmCategory::getId, categoryId);
        BpmCategory bpmCategory = baseMapper.selectOne(queryWrapper, false);
        return bpmCategory != null ? bpmCategory.getName() : "";
    }

    @Override
    public Map<Long, String> getCategoryNameMap(List<Long> categoryIds) {
        if (CollUtil.isEmpty(CollUtil.newHashSet(categoryIds))) {
            return Map.of();
        }

        LambdaQueryWrapper<BpmCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(BpmCategory::getId, BpmCategory::getName);
        queryWrapper.in(BpmCategory::getId, categoryIds);

        List<BpmCategory> categories = baseMapper.selectList(queryWrapper);
        return categories.stream().collect(Collectors.toMap(BpmCategory::getId, BpmCategory::getName));
    }

}
