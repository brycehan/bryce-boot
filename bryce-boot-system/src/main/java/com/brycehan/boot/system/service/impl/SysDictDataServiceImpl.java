package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysDictDataConvert;
import com.brycehan.boot.system.entity.dto.SysDictDataDto;
import com.brycehan.boot.system.entity.dto.SysDictDataPageDto;
import com.brycehan.boot.system.entity.po.SysDictData;
import com.brycehan.boot.system.entity.po.SysDictType;
import com.brycehan.boot.system.entity.vo.SysDictDataSimpleVo;
import com.brycehan.boot.system.entity.vo.SysDictDataVo;
import com.brycehan.boot.system.mapper.SysDictDataMapper;
import com.brycehan.boot.system.service.SysDictDataService;
import com.brycehan.boot.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统字典数据服务实现类
 *
 * @since 2023/09/08
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    private final SysDictTypeService sysDictTypeService;

    /**
     * 添加系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     */
    public void save(SysDictDataDto sysDictDataDto) {
        SysDictData sysDictData = SysDictDataConvert.INSTANCE.convert(sysDictDataDto);
        sysDictData.setId(IdGenerator.nextId());
        baseMapper.insert(sysDictData);
    }

    /**
     * 更新系统字典数据
     *
     * @param sysDictDataDto 系统字典数据Dto
     */
    public void update(SysDictDataDto sysDictDataDto) {
        SysDictData sysDictData = SysDictDataConvert.INSTANCE.convert(sysDictDataDto);
        baseMapper.updateById(sysDictData);
    }

    @Override
    public PageResult<SysDictDataVo> page(SysDictDataPageDto sysDictDataPageDto) {
        IPage<SysDictData> page = baseMapper.selectPage(sysDictDataPageDto.toPage(), getWrapper(sysDictDataPageDto));
        return new PageResult<>(page.getTotal(), SysDictDataConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysDictDataPageDto 系统字典数据分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysDictData> getWrapper(SysDictDataPageDto sysDictDataPageDto) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysDictDataPageDto.getDictTypeId()), SysDictData::getDictTypeId, sysDictDataPageDto.getDictTypeId());
        wrapper.eq(Objects.nonNull(sysDictDataPageDto.getStatus()), SysDictData::getStatus, sysDictDataPageDto.getStatus());

        return wrapper;
    }

    @Override
    public Map<String, List<SysDictDataSimpleVo>> dictMap(StatusType statusType) {
        // 全部字典类型列表
        LambdaQueryWrapper<SysDictType> typeQueryWrapper = new LambdaQueryWrapper<>();
        typeQueryWrapper.select(SysDictType::getId, SysDictType::getDictType);
        typeQueryWrapper.eq(Objects.nonNull(statusType), SysDictType::getStatus, statusType);
        List<SysDictType> typeList = sysDictTypeService.list(typeQueryWrapper);

        // 全部字典数据列表
        LambdaQueryWrapper<SysDictData> dataQueryWrapper = Wrappers.lambdaQuery();
        dataQueryWrapper.select(SysDictData::getDictLabel, SysDictData::getDictValue, SysDictData::getLabelClass, SysDictData::getDictTypeId);
        dataQueryWrapper.eq(Objects.nonNull(statusType), SysDictData::getStatus, statusType);
        dataQueryWrapper.orderByAsc(SysDictData::getSort);
        List<SysDictData> dataList = baseMapper.selectList(dataQueryWrapper);

        // 全部字典列表
        Map<String, List<SysDictDataSimpleVo>> dictMap = new HashMap<>();
        for (SysDictType sysDictType : typeList) {
            List<SysDictDataSimpleVo> list = dataList.stream()
                    .filter(data -> sysDictType.getId().equals(data.getDictTypeId()))
                    .map(data -> new SysDictDataSimpleVo()
                            .setDictLabel(data.getDictLabel())
                            .setDictValue(data.getDictValue())
                            .setLabelClass(data.getLabelClass())
                    )
                    .toList();

            dictMap.put(sysDictType.getDictType(), list);
        }
        return dictMap;
    }

}
