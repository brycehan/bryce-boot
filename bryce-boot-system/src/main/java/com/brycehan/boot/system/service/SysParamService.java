package com.brycehan.boot.system.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.convert.SysParamConvert;
import com.brycehan.boot.system.entity.dto.SysParamDto;
import com.brycehan.boot.system.entity.dto.SysParamKeyDto;
import com.brycehan.boot.system.entity.dto.SysParamPageDto;
import com.brycehan.boot.system.entity.po.SysParam;
import com.brycehan.boot.system.entity.vo.SysParamVo;

/**
 * 系统参数服务
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
public interface SysParamService extends BaseService<SysParam> {

    /**
     * 添加系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    default void save(SysParamDto sysParamDto) {
        SysParam sysParam = SysParamConvert.INSTANCE.convert(sysParamDto);
        sysParam.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysParam);
    }

    /**
     * 更新系统参数
     *
     * @param sysParamDto 系统参数Dto
     */
    default void update(SysParamDto sysParamDto) {
        SysParam sysParam = SysParamConvert.INSTANCE.convert(sysParamDto);
        this.getBaseMapper().updateById(sysParam);
    }

    /**
     * 系统参数分页查询
     *
     * @param sysParamPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysParamVo> page(SysParamPageDto sysParamPageDto);

    /**
     * 系统参数导出数据
     *
     * @param sysParamPageDto 系统参数查询条件
     */
    void export(SysParamPageDto sysParamPageDto);

    boolean exists(String paramKey);

    SysParamVo getByParamKey(String paramKey);

    /**
     * 根据paramKey，查询参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    String getString(String paramKey);

    /**
     * 根据paramKey，查询参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    Integer getInteger(String paramKey);

    /**
     * 根据paramKey，查询参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    boolean getBoolean(String paramKey);

    /**
     * 根据paramKey，查询参数值
     *
     * @param paramKey 参数Key
     * @return 参数值
     */
    <T> T getJSONObject(String paramKey, Class<T> valueType);

    /**
     * 校验参数Key是否唯一
     *
     * @param sysParamKeyDto 参数KeyDto
     * @return true：唯一，false：不唯一
     */
    boolean checkParamKeyUnique(SysParamKeyDto sysParamKeyDto);

}
