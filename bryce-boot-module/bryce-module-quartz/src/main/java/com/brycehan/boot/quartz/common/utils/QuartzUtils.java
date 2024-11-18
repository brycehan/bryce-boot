package com.brycehan.boot.quartz.common.utils;

import cn.hutool.core.date.DateUtil;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.quartz.common.QuartzStatus;
import com.brycehan.boot.quartz.entity.po.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 定时任务工具类
 *
 * @since 2023/10/18
 * @author Bryce Han
 */
@Slf4j
public class QuartzUtils {

    public static final String JOB_NAME = "TASK_NAME_";

    /**
     * 任务调度参数 key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * 创建定时任务
     *
     * @param scheduler 调度器
     * @param quartzJob quartz 任务
     */
    public static void createScheduleJob(Scheduler scheduler, QuartzJob quartzJob) {
        // job key
        JobKey jobKey = getJobKey(quartzJob);
        // 构建 job 信息
        JobDetail jobDetail = JobBuilder.newJob(getJobClass(quartzJob))
                .withIdentity(jobKey).build();

        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression())
                .withMisfireHandlingInstructionDoNothing();

        // 按新的 cronExpression 表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(quartzJob))
                .withSchedule(scheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(JOB_PARAM_KEY, quartzJob);

        try {
            // 把任务添加到 Quartz 中
            scheduler.scheduleJob(jobDetail, trigger);

            // 判断是否存在
            if(scheduler.checkExists(jobKey)) {
                // 防止创建时存在数据问题，先移除，然后再执行创建操作
                scheduler.deleteJob(jobKey);
            }

            // 判断任务是否过期
            if (getNextExecution(quartzJob.getCronExpression()) != null) {
                // 执行调度任务
                scheduler.scheduleJob(jobDetail, trigger);
            }

            // 暂停任务
            if(quartzJob.getStatus() == QuartzStatus.PAUSE) {
                scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("createScheduleJob创建定时任务失败");
            throw new RuntimeException(e);
        }

    }

    /**
     * 更新定时任务
     *
     * @param scheduler 调度器
     * @param quartzJob quartz 任务
     */
    public static void updateSchedulerJob(Scheduler scheduler, QuartzJob quartzJob) {
        // job key
        JobKey jobKey = getJobKey(quartzJob);

        try {
            // 判断是否存在
            if(scheduler.checkExists(jobKey)) {
                // 防止创建时存在数据问题，先移除，然后再执行创建操作
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("updateSchedulerJob创建定时任务失败");
            throw new RuntimeException(e);
        }

        createScheduleJob(scheduler, quartzJob);
    }

    /**
     * 删除定时任务
     *
     * @param scheduler 调度器
     * @param quartzJob quartz 任务
     */
    public static void deleteSchedulerJob(Scheduler scheduler, QuartzJob quartzJob) {
        try {
            scheduler.deleteJob(getJobKey(quartzJob));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 立即执行任务
     *
     * @param scheduler 调度器
     * @param quartzJob quartz 任务
     */
    public static void runJob(Scheduler scheduler, QuartzJob quartzJob) {

        try {
        // 参数
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JOB_PARAM_KEY, quartzJob);

        JobKey jobKey = getJobKey(quartzJob);
            if(scheduler.checkExists(jobKey)) {
                scheduler.triggerJob(jobKey, jobDataMap);
            }
        } catch (SchedulerException e) {
            log.info("执行定时任务失败", e);
            throw new RuntimeException("执行定时任务失败");
        }
    }

    /**
     * 暂停任务
     *
     * @param scheduler 调度器
     * @param quartzJob quartz 任务
     */
    public static void pauseJob(Scheduler scheduler, QuartzJob quartzJob) {
        try {
            scheduler.pauseJob(getJobKey(quartzJob));
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler 调度器
     * @param quartzJob quartz 任务
     */
    public static void resumeJob(Scheduler scheduler, QuartzJob quartzJob) {
        try {
            scheduler.resumeJob(getJobKey(quartzJob));
        } catch (SchedulerException e) {
            log.error("恢复定时任务失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取quartz 任务类
     *
     * @param quartzJob 定时任务
     * @return quartz 任务类
     */
    private static Class<? extends Job> getJobClass(QuartzJob quartzJob) {
        if(quartzJob.getConcurrent().equals(DataConstants.YES)) {
            return ConcurrentQuartzJob.class;
        } else {
            return NonConcurrentQuartzJob.class;
        }
    }

    /**
     * 获取任务 key
     *
     * @param quartzJob quartz 任务
     * @return jobKey
     */
    private static JobKey getJobKey(QuartzJob quartzJob) {
        return JobKey.jobKey(JOB_NAME + quartzJob.getId(), quartzJob.getJobGroup());
    }

    /**
     * 获取触发器 key
     * @param quartzJob quartz 任务
     * @return 触发器 key
     */
    private static TriggerKey getTriggerKey(QuartzJob quartzJob) {
        return TriggerKey.triggerKey(JOB_NAME + quartzJob.getId(), quartzJob.getJobGroup());
    }

    /**
     * 根据给定的 cron 表达式，返回下一个执行时间
     *
     * @param cronExpression cron 表达式
     * @return 下一个执行时间
     */
    public static LocalDateTime getNextExecution(String cronExpression) {
        try {
            CronExpression expression = new CronExpression(cronExpression);
            return DateUtil.toLocalDateTime(expression.getNextValidTimeAfter(new Date()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
