package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.system.dto.SysConfigDto;
import com.brycehan.boot.system.dto.SysConfigPageDto;
import com.brycehan.boot.system.entity.SysConfig;
import com.brycehan.boot.system.vo.SysConfigVo;

/**
 * 系统配置服务类
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 添加系统配置表
     *
     * @param sysConfigDto 系统配置表Dto
     */
    void save(SysConfigDto sysConfigDto);

    /**
     * 更新系统配置表
     *
     * @param sysConfigDto 系统配置表Dto
     */
    void update(SysConfigDto sysConfigDto);

    /**
     * 系统配置表分页查询信息
     *
     * @param sysConfigPageDto 系统配置表分页搜索条件
     * @return 分页信息
     */
    PageResult<SysConfigVo> page(SysConfigPageDto sysConfigPageDto);

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
