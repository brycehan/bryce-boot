package com.brycehan.boot.quartz.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.quartz.entity.QuartzJob;
import com.brycehan.boot.quartz.entity.QuartzJobLog;
import com.brycehan.boot.quartz.service.QuartzJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @since 2023/10/18
 * @author Bryce Han
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    private static final ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();
    @Override
    public void execute(JobExecutionContext context) {
        QuartzJob quartzJob = new QuartzJob();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(QuartzUtils.JOB_PARAM_KEY), quartzJob);

        threadLocal.set(LocalDateTime.now());
        try {
            doExecute(quartzJob);
            saveLog(quartzJob, null);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：{}", quartzJob.getId(), e);
            saveLog(quartzJob, e);
        }

    }


    /**
     * 执行 Spring Bean 方法
     *
     * @param quartzJob 定时任务
     */
    protected void doExecute(QuartzJob quartzJob) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("准备执行任务，任务ID：{}", quartzJob.getId());

        Object bean = SpringUtil.getBean(quartzJob.getBeanName());
        Method method = bean.getClass().getDeclaredMethod(quartzJob.getMethod(), String.class);
        method.invoke(bean, quartzJob.getParams());

        log.info("执行任务完毕，任务ID：{}", quartzJob.getId());
    }

    protected void saveLog(QuartzJob quartzJob, Exception e) {
        LocalDateTime startedTime = threadLocal.get();
        threadLocal.remove();

        // 执行总时长
        long duration = Duration.between(startedTime, LocalDateTime.now()).toMillis();

        // 保存执行记录
        QuartzJobLog quartzJobLog = new QuartzJobLog();
        BeanUtils.copyProperties(quartzJob, quartzJobLog);
        quartzJobLog.setId(IdGenerator.nextId());
        quartzJobLog.setJobId(quartzJob.getId());
        quartzJobLog.setDuration(Long.valueOf(duration).intValue());
        quartzJobLog.setCreatedTime(LocalDateTime.now());

        if (e != null) {
            quartzJobLog.setExecuteStatus(DataConstants.FAIL);
            quartzJobLog.setErrorInfo(ExceptionUtil.stacktraceToString(e));
        } else {
            quartzJobLog.setExecuteStatus(DataConstants.SUCCESS);
        }

        // 保存日志
        SpringUtil.getBean(QuartzJobLogService.class)
                .save(quartzJobLog);
    }
}
