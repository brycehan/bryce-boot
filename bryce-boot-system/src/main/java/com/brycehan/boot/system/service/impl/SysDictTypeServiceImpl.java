package com.brycehan.boot.system.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysDictTypeConvert;
import com.brycehan.boot.system.entity.dto.SysDictTypeCodeDto;
import com.brycehan.boot.system.entity.dto.SysDictTypeDto;
import com.brycehan.boot.system.entity.dto.SysDictTypePageDto;
import com.brycehan.boot.system.entity.po.SysDictType;
import com.brycehan.boot.system.entity.vo.SysDictTypeSimpleVo;
import com.brycehan.boot.system.entity.vo.SysDictTypeVo;
import com.brycehan.boot.system.mapper.SysDictTypeMapper;
import com.brycehan.boot.system.service.SysDictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 系统字典类型服务实现类
 *
 * @author Bryce Han
 * @since 2023/09/05
 */
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    /**
     * 添加系统字典类型
     *
     * @param sysDictTypeDto 系统字典类型Dto
     */
    public void save(SysDictTypeDto sysDictTypeDto) {
        SysDictType sysDictType = SysDictTypeConvert.INSTANCE.convert(sysDictTypeDto);
        sysDictType.setId(IdGenerator.nextId());
        baseMapper.insert(sysDictType);
    }

    /**
     * 更新系统字典类型
     *
     * @param sysDictTypeDto 系统字典类型Dto
     */
    public void update(SysDictTypeDto sysDictTypeDto) {
        SysDictType sysDictType = SysDictTypeConvert.INSTANCE.convert(sysDictTypeDto);
        baseMapper.updateById(sysDictType);
    }

    @Override
    public PageResult<SysDictTypeVo> page(SysDictTypePageDto sysDictTypePageDto) {
        IPage<SysDictType> page = baseMapper.selectPage(sysDictTypePageDto.toPage(), getWrapper(sysDictTypePageDto));
        return new PageResult<>(page.getTotal(), SysDictTypeConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysDictTypePageDto 系统字典类型分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysDictType> getWrapper(SysDictTypePageDto sysDictTypePageDto) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysDictTypePageDto.getStatus()), SysDictType::getStatus, sysDictTypePageDto.getStatus());
        wrapper.like(StringUtils.isNotEmpty(sysDictTypePageDto.getDictName()), SysDictType::getDictName, sysDictTypePageDto.getDictName());
        wrapper.like(StringUtils.isNotEmpty(sysDictTypePageDto.getDictType()), SysDictType::getDictType, sysDictTypePageDto.getDictType());
        addTimeRangeCondition(wrapper, SysDictType::getCreatedTime, sysDictTypePageDto.getCreatedTimeStart(), sysDictTypePageDto.getCreatedTimeEnd());

        return wrapper;
    }

    @Override
    public void export(SysDictTypePageDto sysDictTypePageDto) {
        List<SysDictType> sysDictTypeList = baseMapper.selectList(getWrapper(sysDictTypePageDto));
        List<SysDictTypeVo> sysDictTypeVoList = SysDictTypeConvert.INSTANCE.convert(sysDictTypeList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysDictTypeVo.class, "字典类型_" + today, "字典类型", sysDictTypeVoList);
    }

    @Override
    public List<SysDictTypeSimpleVo> getSimpleList() {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysDictType::getId, SysDictType::getDictType, SysDictType::getDictName);
        queryWrapper.eq(SysDictType::getStatus, StatusType.ENABLE);
        List<SysDictType> sysDictTypeList = baseMapper.selectList(queryWrapper);
        return SysDictTypeConvert.INSTANCE.convertSimple(sysDictTypeList);
    }

    @Override
    public boolean checkDictTypeCodeUnique(SysDictTypeCodeDto sysDictTypeCodeDto) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysDictType::getDictType, SysDictType::getId)
                .eq(SysDictType::getDictType, sysDictTypeCodeDto.getDictType());
        SysDictType sysDictType = baseMapper.selectOne(queryWrapper, false);

        // 修改时，同字典类型编码同ID为编码唯一
        return Objects.isNull(sysDictType) || Objects.equals(sysDictTypeCodeDto.getId(), sysDictType.getId());
    }

}
