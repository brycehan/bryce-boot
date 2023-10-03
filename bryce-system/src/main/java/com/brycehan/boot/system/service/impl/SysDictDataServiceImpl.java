package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysDictDataConvert;
import com.brycehan.boot.system.dto.SysDictDataPageDto;
import com.brycehan.boot.system.entity.SysDictData;
import com.brycehan.boot.system.mapper.SysDictDataMapper;
import com.brycehan.boot.system.service.SysDictDataService;
import com.brycehan.boot.system.vo.SysDictDataVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 系统字典数据服务实现类
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public PageResult<SysDictDataVo> page(SysDictDataPageDto sysDictDataPageDto) {

        IPage<SysDictData> page = this.baseMapper.selectPage(getPage(sysDictDataPageDto), getWrapper(sysDictDataPageDto));

        return new PageResult<>(page.getTotal(), SysDictDataConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysDictDataPageDto 系统字典数据分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysDictData> getWrapper(SysDictDataPageDto sysDictDataPageDto){
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysDictDataPageDto.getDictTypeId()), SysDictData::getDictTypeId, sysDictDataPageDto.getDictTypeId());
        wrapper.eq(Objects.nonNull(sysDictDataPageDto.getStatus()), SysDictData::getStatus, sysDictDataPageDto.getStatus());
        wrapper.eq(Objects.nonNull(sysDictDataPageDto.getTenantId()), SysDictData::getTenantId, sysDictDataPageDto.getTenantId());
        return wrapper;
    }

    @Override
    public void export(SysDictDataPageDto sysDictDataPageDto) {
        List<SysDictData> sysDictDataList = this.baseMapper.selectList(getWrapper(sysDictDataPageDto));
        List<SysDictDataVo> sysDictDataVoList = SysDictDataConvert.INSTANCE.convert(sysDictDataList);
        ExcelUtils.export(SysDictDataVo.class, "系统字典数据", "系统字典数据", sysDictDataVoList);
    }

}
