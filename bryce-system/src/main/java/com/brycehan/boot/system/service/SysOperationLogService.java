package com.brycehan.boot.system.service;

import com.brycehan.boot.system.dto.SysOperationLogPageDto;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import com.github.pagehelper.PageInfo;
import com.brycehan.boot.system.entity.SysOperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统操作日志服务类
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
@Validated
public interface SysOperationLogService extends IService<SysOperationLog> {
    
    /**
     * 分页查询信息结果
     *
     * @param sysOperationLogPageDto 搜索条件
     * @return 分页信息
     */
    PageInfo<SysOperationLog> page(@NotNull SysOperationLogPageDto sysOperationLogPageDto);
}
