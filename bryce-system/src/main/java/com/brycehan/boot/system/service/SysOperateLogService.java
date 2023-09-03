package com.brycehan.boot.system.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.dto.SysOperateLogPageDto;
import com.brycehan.boot.system.entity.SysOperateLog;
import com.brycehan.boot.system.vo.SysOperateLogVo;

/**
 * 系统操作日志服务
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
public interface SysOperateLogService extends BaseService<SysOperateLog> {

    /**
     * 系统操作日志分页查询
     *
     * @param sysOperateLogPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysOperateLogVo> page(SysOperateLogPageDto sysOperateLogPageDto);

}
