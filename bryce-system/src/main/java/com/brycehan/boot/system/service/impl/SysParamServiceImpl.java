package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysParamConvert;
import com.brycehan.boot.system.dto.SysParamPageDto;
import com.brycehan.boot.system.entity.SysParam;
import com.brycehan.boot.system.mapper.SysParamMapper;
import com.brycehan.boot.system.service.SysParamService;
import com.brycehan.boot.system.vo.SysParamVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 系统参数服务实现
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysParamServiceImpl extends BaseServiceImpl<SysParamMapper, SysParam> implements SysParamService {

    private final StringRedisTemplate stringRedisTemplate;
    
    @Override
    public PageResult<SysParamVo> page(SysParamPageDto sysParamPageDto) {

        IPage<SysParam> page = this.baseMapper.selectPage(getPage(sysParamPageDto), getWrapper(sysParamPageDto));

        return new PageResult<>(page.getTotal(), SysParamConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysParamPageDto 系统参数分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysParam> getWrapper(SysParamPageDto sysParamPageDto){
        LambdaQueryWrapper<SysParam> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(sysParamPageDto.getParamName()), SysParam::getParamName, sysParamPageDto.getParamName());
        wrapper.like(StringUtils.isNotEmpty(sysParamPageDto.getParamKey()), SysParam::getParamKey, sysParamPageDto.getParamKey());
        wrapper.eq(StringUtils.isNotEmpty(sysParamPageDto.getBuiltIn()), SysParam::getBuiltIn, sysParamPageDto.getBuiltIn());
        wrapper.eq(Objects.nonNull(sysParamPageDto.getTenantId()), SysParam::getTenantId, sysParamPageDto.getTenantId());

        if(sysParamPageDto.getCreatedTimeStart() != null && sysParamPageDto.getCreatedTimeEnd() != null) {
            wrapper.between(SysParam::getCreatedTime, sysParamPageDto.getCreatedTimeStart(), sysParamPageDto.getCreatedTimeEnd());
        } else if(sysParamPageDto.getCreatedTimeStart() != null) {
            wrapper.ge(SysParam::getCreatedTime, sysParamPageDto.getCreatedTimeStart());
        }else if(sysParamPageDto.getCreatedTimeEnd() != null) {
            wrapper.ge(SysParam::getCreatedTime, sysParamPageDto.getCreatedTimeEnd());
        }

        return wrapper;
    }

    @Override
    public void export(SysParamPageDto sysParamPageDto) {
        List<SysParam> sysParamList = this.baseMapper.selectList(getWrapper(sysParamPageDto));
        List<SysParamVo> sysParamVoList = SysParamConvert.INSTANCE.convert(sysParamList);
        ExcelUtils.export(SysParamVo.class, "系统参数_".concat(DateTimeUtils.today()), "系统参数", sysParamVoList);
    }

    // todo 优化
    @Override
    public String selectParamValueByParamKey(String paramKey) {
        // 1、从缓存中查询
        String cacheKey = getCacheKey(paramKey);
        String configValue = this.stringRedisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        // 2、缓存没有时，从数据库中查询
        LambdaQueryWrapper<SysParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysParam::getParamValue)
                .eq(SysParam::getParamKey, paramKey)
                .last("limit 1");

        SysParam sysParam = this.baseMapper.selectOne(queryWrapper);
        if (Objects.nonNull(sysParam)) {
            // 3、添加到缓存中
            this.stringRedisTemplate.opsForValue().set(getCacheKey(paramKey), sysParam.getParamValue());
            return sysParam.getParamValue();
        }

        return StringUtils.EMPTY;
    }

    @Override
    public boolean selectCaptchaEnabled() {
        LambdaQueryWrapper<SysParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysParam::getParamValue)
                .eq(SysParam::getParamKey, "system.account.captchaEnabled")
                .last("limit 1");

        SysParam sysParam = getOne(queryWrapper);
        if (Objects.nonNull(sysParam)) {
            return Boolean.parseBoolean(sysParam.getParamValue());
        }
        return false;
    }

    @Override
    public boolean selectSmsEnabled() {
        LambdaQueryWrapper<SysParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysParam::getParamValue)
                .eq(SysParam::getParamKey, "system.account.smsEnabled")
                .last("limit 1");

        SysParam sysParam = getOne(queryWrapper);
        if (Objects.nonNull(sysParam)) {
            return Boolean.parseBoolean(sysParam.getParamValue());
        }
        return false;
    }

    public static String getCacheKey(String paramKey) {
        return CacheConstants.SYS_CONFIG_KEY + paramKey;
    }
    
}
