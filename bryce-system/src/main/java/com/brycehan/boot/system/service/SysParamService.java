package com.brycehan.boot.system.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.system.convert.SysParamConvert;
import com.brycehan.boot.system.dto.SysParamDto;
import com.brycehan.boot.system.dto.SysParamPageDto;
import com.brycehan.boot.system.entity.SysParam;
import com.brycehan.boot.system.vo.SysParamVo;

/**
 * 系统参数服务
 *
 * @author Bryce Han
 * @since 2023/09/28
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

    /**
     * 根据键名查询参数值
     *
     * @param paramKey 参数键名
     * @return 参数值
     */
    String selectParamValueByParamKey(String paramKey);

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

}
