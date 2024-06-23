package com.brycehan.boot.system.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.api.system.SysParamApi;
import com.brycehan.boot.api.system.dto.SysParamDto;
import com.brycehan.boot.api.system.vo.SysParamVo;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.system.entity.po.SysParam;
import com.brycehan.boot.system.service.SysParamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 系统参数 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysParamApiService implements SysParamApi {

    private final SysParamService sysParamService;

    @Override
    public void save(SysParamDto sysParamDto) {
        SysParam sysParam = new SysParam();
        BeanUtils.copyProperties(sysParamDto, sysParam);
        sysParam.setId(IdGenerator.nextId());
        this.sysParamService.save(sysParam);
    }

    @Override
    public void update(SysParamDto sysParamDto) {
        SysParam sysParam = new SysParam();
        BeanUtils.copyProperties(sysParamDto, sysParam);
        // 通过paramKey更新时
        if (StringUtils.isNotEmpty(sysParam.getParamKey())) {
            LambdaQueryWrapper<SysParam> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(SysParam::getId);
            queryWrapper.eq(SysParam::getParamKey, sysParam.getParamKey());
            SysParam param = this.sysParamService.getOne(queryWrapper, false);
            if (param != null) {
                sysParam.setId(param.getId());
            }
        }

        if (sysParamDto.getId() == null) {
            return;
        }

        this.sysParamService.updateById(sysParam);
    }

    @Override
    public Boolean exists(String paramKey) {
        return this.sysParamService.exists(paramKey);
    }

    @Override
    public SysParamVo getByParamKey(String paramKey) {
        LambdaQueryWrapper<SysParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysParam::getParamKey, paramKey);
        SysParam sysParam = this.sysParamService.getOne(queryWrapper, false);

        SysParamVo sysParamVo = new SysParamVo();
        BeanUtils.copyProperties(sysParam, sysParamVo);

        return sysParamVo;
    }

    @Override
    public String getString(String paramKey) {
        return this.sysParamService.getString(paramKey);
    }

    @Override
    public Boolean getBoolean(String paramKey) {
        return this.sysParamService.getBoolean(paramKey);
    }

}
