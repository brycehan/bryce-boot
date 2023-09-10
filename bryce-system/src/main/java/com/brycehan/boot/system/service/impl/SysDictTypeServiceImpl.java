package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysDictTypeConvert;
import com.brycehan.boot.system.dto.SysDictTypePageDto;
import com.brycehan.boot.system.entity.SysDictType;
import com.brycehan.boot.system.mapper.SysDictTypeMapper;
import com.brycehan.boot.system.service.SysDictTypeService;
import com.brycehan.boot.system.vo.SysDictTypeVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 系统字典类型服务实现类
 *
 * @author Bryce Han
 * @since 2023/09/05
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Override
    public PageResult<SysDictTypeVo> page(SysDictTypePageDto sysDictTypePageDto) {

        IPage<SysDictType> page = this.baseMapper.selectPage(getPage(sysDictTypePageDto), getWrapper(sysDictTypePageDto));

        return new PageResult<>(page.getTotal(), SysDictTypeConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysDictTypePageDto 系统字典类型分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysDictType> getWrapper(SysDictTypePageDto sysDictTypePageDto){
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysDictTypePageDto.getStatus()), SysDictType::getStatus, sysDictTypePageDto.getStatus());
        wrapper.eq(Objects.nonNull(sysDictTypePageDto.getTenantId()), SysDictType::getTenantId, sysDictTypePageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(sysDictTypePageDto.getDictName()), SysDictType::getDictName, sysDictTypePageDto.getDictName());
        wrapper.like(StringUtils.isNotEmpty(sysDictTypePageDto.getDictType()), SysDictType::getDictType, sysDictTypePageDto.getDictType());

        if(sysDictTypePageDto.getCreatedTimeStart() != null && sysDictTypePageDto.getCreatedTimeEnd() != null) {
            wrapper.between(SysDictType::getCreatedTime, sysDictTypePageDto.getCreatedTimeStart(), sysDictTypePageDto.getCreatedTimeEnd());
        } else if(sysDictTypePageDto.getCreatedTimeStart() != null) {
            wrapper.ge(SysDictType::getCreatedTime, sysDictTypePageDto.getCreatedTimeStart());
        }else if(sysDictTypePageDto.getCreatedTimeEnd() != null) {
            wrapper.ge(SysDictType::getCreatedTime, sysDictTypePageDto.getCreatedTimeEnd());
        }

        return wrapper;
    }

    @Override
    public void export(SysDictTypePageDto sysDictTypePageDto) {
        List<SysDictType> sysDictTypeList = this.baseMapper.selectList(getWrapper(sysDictTypePageDto));
        List<SysDictTypeVo> sysDictTypeVoList = SysDictTypeConvert.INSTANCE.convert(sysDictTypeList);
        ExcelUtils.export(SysDictTypeVo.class, "系统字典类型", "系统字典类型", sysDictTypeVoList);
    }

}
