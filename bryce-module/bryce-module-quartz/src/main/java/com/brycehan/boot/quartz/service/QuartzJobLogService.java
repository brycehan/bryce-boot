package com.brycehan.boot.quartz.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.quartz.convert.QuartzJobLogConvert;
import com.brycehan.boot.quartz.dto.QuartzJobLogDto;
import com.brycehan.boot.quartz.dto.QuartzJobLogPageDto;
import com.brycehan.boot.quartz.entity.QuartzJobLog;
import com.brycehan.boot.quartz.vo.QuartzJobLogVo;

/**
 * quartz 定时任务调度日志服务
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
public interface QuartzJobLogService extends BaseService<QuartzJobLog> {

    /**
     * 添加quartz 定时任务调度日志
     *
     * @param quartzJobLogDto quartz 定时任务调度日志Dto
     */
    default void save(QuartzJobLogDto quartzJobLogDto) {
        QuartzJobLog quartzJobLog = QuartzJobLogConvert.INSTANCE.convert(quartzJobLogDto);
        quartzJobLog.setId(IdGenerator.nextId());
        this.getBaseMapper().insert(quartzJobLog);
    }

    /**
     * 更新quartz 定时任务调度日志
     *
     * @param quartzJobLogDto quartz 定时任务调度日志Dto
     */
    default void update(QuartzJobLogDto quartzJobLogDto) {
        QuartzJobLog quartzJobLog = QuartzJobLogConvert.INSTANCE.convert(quartzJobLogDto);
        this.getBaseMapper().updateById(quartzJobLog);
    }

    /**
     * quartz 定时任务调度日志分页查询
     *
     * @param quartzJobLogPageDto 查询条件
     * @return 分页信息
     */
    PageResult<QuartzJobLogVo> page(QuartzJobLogPageDto quartzJobLogPageDto);

    /**
     * quartz 定时任务调度日志导出数据
     *
     * @param quartzJobLogPageDto quartz 定时任务调度日志查询条件
     */
    void export(QuartzJobLogPageDto quartzJobLogPageDto);

}
