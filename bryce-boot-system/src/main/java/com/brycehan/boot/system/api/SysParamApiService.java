package com.brycehan.boot.system.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.api.system.SysParamApi;
import com.brycehan.boot.api.system.dto.SysParamApiDto;
import com.brycehan.boot.api.system.vo.SysParamApiVo;
import com.brycehan.boot.system.convert.SysParamConvert;
import com.brycehan.boot.system.dto.SysParamDto;
import com.brycehan.boot.system.entity.SysParam;
import com.brycehan.boot.system.service.SysParamService;
import com.brycehan.boot.system.vo.SysParamVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void save(SysParamApiDto sysParamApiDto) {
        SysParamDto sysParam = new SysParamDto();
        BeanUtils.copyProperties(sysParamApiDto, sysParam);
        this.sysParamService.save(sysParam);
    }

    @Override
    public void update(SysParamApiDto sysParamApiDto) {
        SysParamDto sysParam = new SysParamDto();
        BeanUtils.copyProperties(sysParamApiDto, sysParam);
        // 通过paramKey更新时
        if (sysParam.getId() == null) {
            LambdaQueryWrapper<SysParam> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysParam::getParamKey, sysParam.getParamKey());

            this.sysParamService.update(SysParamConvert.INSTANCE.convert(sysParam), queryWrapper);
            return;
        }
        this.sysParamService.update(sysParam);
    }

    @Override
    public Boolean exists(String paramKey) {
        return this.sysParamService.exists(paramKey);
    }

    @Override
    public SysParamApiVo getByParamKey(String paramKey) {
        SysParamVo sysParamVo = this.sysParamService.getByParamKey(paramKey);

        SysParamApiVo sysParamApiVo = new SysParamApiVo();
        BeanUtils.copyProperties(sysParamVo, sysParamApiVo);

        return sysParamApiVo;
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
