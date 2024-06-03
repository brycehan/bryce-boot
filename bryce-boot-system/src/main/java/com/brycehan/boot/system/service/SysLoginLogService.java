package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.convert.SysLoginLogConvert;
import com.brycehan.boot.system.entity.dto.SysLoginLogDto;
import com.brycehan.boot.system.entity.dto.SysLoginLogPageDto;
import com.brycehan.boot.system.entity.po.SysLoginLog;
import com.brycehan.boot.system.entity.vo.SysLoginLogVo;

/**
 * 系统登录日志服务
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
public interface SysLoginLogService extends BaseService<SysLoginLog> {

    /**
     * 添加系统登录日志
     *
     * @param sysLoginLogDto 系统登录日志Dto
     */
    default void save(SysLoginLogDto sysLoginLogDto) {
        SysLoginLog sysLoginLog = SysLoginLogConvert.INSTANCE.convert(sysLoginLogDto);
        sysLoginLog.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysLoginLog);
    }

    /**
     * 更新系统登录日志
     *
     * @param sysLoginLogDto 系统登录日志Dto
     */
    default void update(SysLoginLogDto sysLoginLogDto) {
        SysLoginLog sysLoginLog = SysLoginLogConvert.INSTANCE.convert(sysLoginLogDto);
        this.getBaseMapper().updateById(sysLoginLog);
    }

    /**
     * 系统登录日志分页查询
     *
     * @param sysLoginLogPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysLoginLogVo> page(SysLoginLogPageDto sysLoginLogPageDto);

    /**
     * 系统登录日志导出数据
     *
     * @param sysLoginLogPageDto 系统登录日志查询条件
     */
    void export(SysLoginLogPageDto sysLoginLogPageDto);

    /**
     * 保存登录日志
     *
     * @param username 用户账号
     * @param status   登录状态
     * @param info     操作信息
     */
    void save(String username, boolean status, Integer info);
}
