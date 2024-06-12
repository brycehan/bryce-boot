package com.brycehan.boot.api.system;

import com.brycehan.boot.api.system.dto.SysParamDto;
import com.brycehan.boot.api.system.vo.SysParamVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统参数 Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface SysParamApi {

    /**
     * 添加系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    void save(@RequestBody SysParamDto sysParamDto);

    /**
     * 更新系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    void update(@RequestBody SysParamDto sysParamDto);

    /**
     * 判断 paramKey 是否存在
     *
     * @param paramKey 参数key
     *
     * @return paramKey 是否存在
     */
    Boolean exists(@RequestParam String paramKey);

    /**
     * 获取参数对象
     *
     * @param paramKey 参数key
     * @return 参数对象
     */
    SysParamVo getByParamKey(@RequestParam String paramKey);

    /**
     * 根据paramKey，查询字符串类型的参数值
     *
     * @param paramKey 参数key
     * @return 参数值
     */
    String getString(@RequestParam String paramKey);

    /**
     * 根据paramKey，查询boolean类型的参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    Boolean getBoolean(@RequestParam String paramKey);

}
