package com.brycehan.boot.quartz.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.quartz.common.JobStatus;
import com.brycehan.boot.quartz.entity.dto.QuartzJobDto;
import com.brycehan.boot.quartz.entity.dto.QuartzJobPageDto;
import com.brycehan.boot.quartz.entity.po.QuartzJob;
import com.brycehan.boot.quartz.entity.vo.QuartzJobVo;

/**
 * quartz定时任务调度服务
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
public interface QuartzJobService extends BaseService<QuartzJob> {

    /**
     * 添加quartz定时任务调度
     *
     * @param quartzJobDto quartz定时任务调度Dto
     */
    void save(QuartzJobDto quartzJobDto);

    /**
     * 更新quartz定时任务调度
     *
     * @param quartzJobDto quartz定时任务调度Dto
     */
    void update(QuartzJobDto quartzJobDto);

    /**
     * quartz定时任务调度分页查询
     *
     * @param quartzJobPageDto 查询条件
     * @return 分页信息
     */
    PageResult<QuartzJobVo> page(QuartzJobPageDto quartzJobPageDto);

    /**
     * quartz定时任务调度导出数据
     *
     * @param quartzJobPageDto quartz定时任务调度查询条件
     */
    void export(QuartzJobPageDto quartzJobPageDto);

    /**
     * quartz定时任务立即执行
     *
     * @param quartzJobDto quartz定时任务
     */
    void run(QuartzJobDto quartzJobDto);

    /**
     * 修改quartz定时任务状态
     * @param id quartz定时任务调度id
     * @param status quartz定时任务调度状态
     */
    void changeStatus(Long id, JobStatus status);

}
