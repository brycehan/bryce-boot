package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.convert.SysOperateLogConvert;
import com.brycehan.boot.system.entity.dto.SysOperateLogDto;
import com.brycehan.boot.system.entity.dto.SysOperateLogPageDto;
import com.brycehan.boot.system.entity.po.SysOperateLog;
import com.brycehan.boot.system.entity.vo.SysOperateLogVo;

/**
 * 系统操作日志服务
 *
 * @since 2022/11/18
 * @author Bryce Han
 */
public interface SysOperateLogService extends BaseService<SysOperateLog> {

    /**
     * 添加系统操作日志
     *
     * @param sysOperateLogDto 系统操作日志Dto
     */
    default void save(SysOperateLogDto sysOperateLogDto) {
        SysOperateLog sysOperateLog = SysOperateLogConvert.INSTANCE.convert(sysOperateLogDto);
        sysOperateLog.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(sysOperateLog);
    }

    /**
     * 更新系统操作日志
     *
     * @param sysOperateLogDto 系统操作日志Dto
     */
    default void update(SysOperateLogDto sysOperateLogDto) {
        SysOperateLog sysOperateLog = SysOperateLogConvert.INSTANCE.convert(sysOperateLogDto);
        this.getBaseMapper().updateById(sysOperateLog);
    }

    /**
     * 查询系统操作日志详情
     *
     * @param id 系统操作日志ID
     * @return 系统操作日志VO
     */
    SysOperateLogVo get(Long id);

    /**
     * 系统操作日志分页查询
     *
     * @param sysOperateLogPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysOperateLogVo> page(SysOperateLogPageDto sysOperateLogPageDto);

    /**
     * 系统操作日志导出数据
     *
     * @param sysOperateLogPageDto 系统操作日志查询条件
     */
    void export(SysOperateLogPageDto sysOperateLogPageDto);

}
