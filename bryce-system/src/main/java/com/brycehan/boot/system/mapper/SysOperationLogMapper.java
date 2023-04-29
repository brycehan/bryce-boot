package com.brycehan.boot.system.mapper;

import java.util.List;
import com.brycehan.boot.system.entity.SysOperationLog;
import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.dto.SysOperationLogPageDto;
import org.apache.ibatis.annotations.Mapper;

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
