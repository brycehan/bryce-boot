package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.system.dto.SysConfigPageDto;
import com.brycehan.boot.system.entity.SysConfig;
import com.github.pagehelper.PageInfo;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 系统配置服务类
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
@Validated
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 分页查询信息结果
     *
     * @param sysConfigPageDto 搜索条件
     * @return 分页信息
     */
    PageInfo<SysConfig> page(@NotNull SysConfigPageDto sysConfigPageDto);

    /**
     * 根据键名查询参数配置
     *
     * @param configKey 参数键
     * @return 参数值
     */
    String selectConfigValueByConfigKey(String configKey);

    /**
     * 获取图片验证码开关
     *
     * @return true：开启，false：关闭
     */
    boolean selectCaptchaEnabled();

    /**
     * 获取短信验证码开关
     *
     * @return true：开启，false：关闭
     */
    boolean selectSmsEnabled();

    // todo 缓存
}
