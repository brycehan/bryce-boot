package com.brycehan.boot.quartz.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.quartz.entity.po.QuartzJobLog;
import org.apache.ibatis.annotations.Mapper;

/**
* quartz定时任务调度日志Mapper接口
*
* @author Bryce Han
* @since 2023/10/19
*/
@Mapper
public interface QuartzJobLogMapper extends BryceBaseMapper<QuartzJobLog> {

}
