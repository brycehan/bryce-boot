package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.system.dto.SysConfigPageDto;
import com.brycehan.boot.system.entity.SysConfig;
import com.brycehan.boot.system.mapper.SysConfigMapper;
import com.brycehan.boot.system.service.SysConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Objects;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 系统配置服务实现类
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final SysConfigMapper sysConfigMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public SysConfigServiceImpl(SysConfigMapper sysConfigMapper) {
        this.sysConfigMapper = sysConfigMapper;
    }

    /**
     * 查询多条数据
     *
     * @param sysConfigPageDto 分页查询数据对象
     * @return 对象列表
     */
    @Override
    public PageInfo<SysConfig> page(SysConfigPageDto sysConfigPageDto) {

        //1.根据page、size初始化分页参数，此时分页插件内部会将这两个参数放到ThreadLocal线程上下文中
        Page<SysConfig> page = startPage(sysConfigPageDto.getCurrent(), sysConfigPageDto.getPageSize());

        /*
          2.紧跟初始化代码，查询业务数据
          2.1.分页拦截器根据page、size参数改写sql语句，生成count并查询
          2.2.分页拦截器执行实际的业务查询
          2.3.分页拦截器将2.1.的总记录数与2.2查询的分页数据封装到1中的page
          2.4.分页拦截器清空ThreadLocal上下文中记录的分页参数信息，防止内存泄漏
         */
        this.sysConfigMapper.page(sysConfigPageDto);

        //3.转换为PageInfo后返回
        return new PageInfo<>(page);
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
        SysConfig sysConfig = this.sysConfigMapper.selectOne(queryWrapper);
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
