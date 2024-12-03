package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysOperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 系统操作日志Mapper接口
 *
 * @since 2023/09/27
 * @author Bryce Han
 */
@Mapper
@SuppressWarnings("all")
public interface SysOperateLogMapper extends BryceBaseMapper<SysOperateLog> {

    /**
     * 清理系统操作日志
     */
    @Update("""
            truncate table brc_sys_operate_log""")
    void cleanOperateLog();

}
