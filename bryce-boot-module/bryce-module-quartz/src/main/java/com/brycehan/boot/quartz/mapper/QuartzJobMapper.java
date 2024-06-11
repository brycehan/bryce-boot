package com.brycehan.boot.quartz.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.quartz.entity.po.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

/**
* quartz定时任务调度Mapper接口
*
* @author Bryce Han
* @since 2023/10/17
*/
@Mapper
public interface QuartzJobMapper extends BryceBaseMapper<QuartzJob> {

}
