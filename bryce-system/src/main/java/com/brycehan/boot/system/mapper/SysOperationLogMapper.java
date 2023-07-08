package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.dto.SysOperationLogPageDto;
import com.brycehan.boot.system.entity.SysOperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统操作日志Mapper接口
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
@Mapper
public interface SysOperationLogMapper extends BryceBaseMapper<SysOperationLog> {
    
        /**
         * 分页查询
         *
         * @param sysOperationLogPageDto
         * @return
         */
        List<SysOperationLog> page(SysOperationLogPageDto sysOperationLogPageDto);
}
