package com.brycehan.boot.system.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.api.system.SysAreaCodeApi;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysAreaCodeConvert;
import com.brycehan.boot.system.entity.dto.SysAreaCodeDto;
import com.brycehan.boot.system.entity.dto.SysAreaCodePageDto;
import com.brycehan.boot.system.entity.po.SysAreaCode;
import com.brycehan.boot.system.entity.vo.SysAreaCodeVo;
import com.brycehan.boot.system.mapper.SysAreaCodeMapper;
import com.brycehan.boot.system.service.SysAreaCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 地区编码服务实现
 *
 * @author Bryce Han
 * @since 2024/04/12
 */
@Service
@RequiredArgsConstructor
public class SysAreaCodeServiceImpl extends BaseServiceImpl<SysAreaCodeMapper, SysAreaCode> implements SysAreaCodeService, SysAreaCodeApi {

    /**
     * 更新地区编码
     *
     * @param sysAreaCodeDto 地区编码Dto
     */
    public void update(SysAreaCodeDto sysAreaCodeDto) {
        SysAreaCode sysAreaCode = SysAreaCodeConvert.INSTANCE.convert(sysAreaCodeDto);
        this.baseMapper.updateById(sysAreaCode);
    }

    @Override
    public PageResult<SysAreaCodeVo> page(SysAreaCodePageDto sysAreaCodePageDto) {
        IPage<SysAreaCode> page = this.baseMapper.selectPage(sysAreaCodePageDto.toPage(), getWrapper(sysAreaCodePageDto));
        return new PageResult<>(page.getTotal(), SysAreaCodeConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysAreaCodePageDto 地区编码分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<SysAreaCode> getWrapper(SysAreaCodePageDto sysAreaCodePageDto){
        LambdaQueryWrapper<SysAreaCode> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(sysAreaCodePageDto.getName()), SysAreaCode::getName, sysAreaCodePageDto.getName());
        return wrapper;
    }

    @Override
    public void export(SysAreaCodePageDto sysAreaCodePageDto) {
        List<SysAreaCode> sysAreaCodeList = this.baseMapper.selectList(getWrapper(sysAreaCodePageDto));
        List<SysAreaCodeVo> sysAreaCodeVoList = SysAreaCodeConvert.INSTANCE.convert(sysAreaCodeList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysAreaCodeVo.class, "地区编码_".concat(today), "地区编码", sysAreaCodeVoList);
    }

    @Override
    public SysAreaCodeVo getByCode(String areaCode) {
        LambdaQueryWrapper<SysAreaCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAreaCode::getCode, areaCode);
        SysAreaCode sysAreaCode = this.baseMapper.selectOne(queryWrapper, false);

        return SysAreaCodeConvert.INSTANCE.convert(sysAreaCode);
    }

    @Override
    public String getNameByCode(String areaCode) {
        LambdaQueryWrapper<SysAreaCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAreaCode::getCode, areaCode);
        SysAreaCode sysAreaCode = this.baseMapper.selectOne(queryWrapper, false);

        if (sysAreaCode == null) {
            return null;
        }

        return sysAreaCode.getName();
    }

    @Override
    public String getExtNameByCode(String areaCode) {
        LambdaQueryWrapper<SysAreaCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAreaCode::getCode, areaCode);
        SysAreaCode sysAreaCode = this.baseMapper.selectOne(queryWrapper, false);

        if (sysAreaCode == null) {
            return null;
        }

        return sysAreaCode.getExtName();
    }

    @Override
    public String getFullLocation(String areaCode) {
        if (StringUtils.isEmpty(areaCode) || areaCode.length() != 6 && areaCode.length() != 9) {
            return null;
        }
        // 获取省市区
        Integer provinceId = Integer.valueOf(areaCode.substring(0, 2));
        Integer cityId = Integer.valueOf(areaCode.substring(0, 4));
        Integer countyId = Integer.valueOf(areaCode.substring(0, 6));
        if (areaCode.length() == 6) {
            LambdaQueryWrapper<SysAreaCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysAreaCode::getId, provinceId, cityId, countyId);
            queryWrapper.orderByAsc(SysAreaCode::getId);

            List<SysAreaCode> areaCodes = this.baseMapper.selectList(queryWrapper);

            if (!CollectionUtils.isEmpty(areaCodes)) {
                return areaCodes.stream().map(SysAreaCode::getExtName)
                        .collect(Collectors.joining(" "));
            }
        } else {
            Integer unitId = Integer.valueOf(areaCode);

            LambdaQueryWrapper<SysAreaCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysAreaCode::getId, provinceId, cityId, countyId, unitId);
            queryWrapper.orderByAsc(SysAreaCode::getId);

            List<SysAreaCode> areaCodes = this.baseMapper.selectList(queryWrapper);

            if (!CollectionUtils.isEmpty(areaCodes)) {
                return areaCodes.stream().map(SysAreaCode::getName)
                        .collect(Collectors.joining(" "));
            }
        }

        return null;
    }

    @Override
    public List<String> getFullLocation(List<String> areaCodeList) {
        if (CollectionUtils.isEmpty(areaCodeList)) {
            return null;
        }

        return areaCodeList.parallelStream()
                .map(this::getFullLocation)
                .toList();
    }


}
