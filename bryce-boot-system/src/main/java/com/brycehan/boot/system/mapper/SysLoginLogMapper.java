package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 系统登录日志Mapper接口
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Mapper
@SuppressWarnings("all")
public interface SysLoginLogMapper extends BryceBaseMapper<SysLoginLog> {

    /**
     * 清空系统登录日志
     */
    @Update("""
            truncate table brc_sys_login_log""")
    void cleanLoginLog();

}
