package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysConfigConvert;
import com.brycehan.boot.system.dto.SysConfigDto;
import com.brycehan.boot.system.dto.SysConfigPageDto;
import com.brycehan.boot.system.entity.SysConfig;
import com.brycehan.boot.system.mapper.SysConfigMapper;
import com.brycehan.boot.system.service.SysConfigService;
import com.brycehan.boot.system.vo.SysConfigVo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统配置服务实现类
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(SysConfigDto sysConfigDto) {
        SysConfig sysConfig = SysConfigConvert.INSTANCE.convert(sysConfigDto);
        sysConfig.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysConfig);
    }

    @Override
    public void update(SysConfigDto sysConfigDto) {
        SysConfig sysConfig = SysConfigConvert.INSTANCE.convert(sysConfigDto);
        this.baseMapper.updateById(sysConfig);
    }

    @Override
    public PageResult<SysConfigVo> page(SysConfigPageDto sysConfigPageDto) {

        IPage<SysConfig> page = this.baseMapper.selectPage(getPage(sysConfigPageDto), getWrapper(sysConfigPageDto));

        return new PageResult<>(page.getTotal(), SysConfigConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysConfigPageDto 系统配置表分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysConfig> getWrapper(SysConfigPageDto sysConfigPageDto){
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    // todo 优化
    @Override
    public String selectConfigValueByConfigKey(String configKey) {
        // 1、从缓存中查询
        String cacheKey = getCacheKey(configKey);
        String configValue = this.stringRedisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        // 2、缓存没有时，从数据库中查询
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("config_value")
                .eq("config_key", configKey)
                .last("limit 1");
        SysConfig sysConfig = this.baseMapper.selectOne(queryWrapper);
        if (Objects.nonNull(sysConfig)) {
            // 3、添加到缓存中
            this.stringRedisTemplate.opsForValue().set(getCacheKey(configKey), sysConfig.getConfigValue());
            return sysConfig.getConfigValue();
        }

        return StringUtils.EMPTY;
    }

    @Override
    public boolean selectCaptchaEnabled() {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("config_value")
                .eq("config_key", "sys.account.captchaEnabled")
                .last("limit 1");
        SysConfig sysConfig = getOne(queryWrapper);
        if (Objects.nonNull(sysConfig)) {
            return Boolean.parseBoolean(sysConfig.getConfigValue());
        }
        return false;
    }

    @Override
    public boolean selectSmsEnabled() {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("config_value")
                .eq("config_key", "sys.account.smsEnabled")
                .last("limit 1");
        SysConfig sysConfig = getOne(queryWrapper);
        if (Objects.nonNull(sysConfig)) {
            return Boolean.parseBoolean(sysConfig.getConfigValue());
        }
        return false;
    }

    public static String getCacheKey(String configKey) {
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }

}
