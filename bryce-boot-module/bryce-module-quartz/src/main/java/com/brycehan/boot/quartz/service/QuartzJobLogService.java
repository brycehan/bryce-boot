package com.brycehan.boot.quartz.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.quartz.entity.dto.QuartzJobLogPageDto;
import com.brycehan.boot.quartz.entity.po.QuartzJobLog;
import com.brycehan.boot.quartz.entity.vo.QuartzJobLogVo;

/**
 * quartz定时任务调度日志服务
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
public interface QuartzJobLogService extends BaseService<QuartzJobLog> {

    /**
     * quartz定时任务调度日志分页查询
     *
     * @param quartzJobLogPageDto 查询条件
     * @return 分页信息
     */
    PageResult<QuartzJobLogVo> page(QuartzJobLogPageDto quartzJobLogPageDto);

}
