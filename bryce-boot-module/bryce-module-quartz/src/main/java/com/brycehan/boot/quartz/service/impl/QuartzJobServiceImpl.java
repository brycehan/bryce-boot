package com.brycehan.boot.quartz.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.quartz.common.JobStatus;
import com.brycehan.boot.quartz.common.utils.QuartzUtils;
import com.brycehan.boot.quartz.entity.convert.QuartzJobConvert;
import com.brycehan.boot.quartz.entity.dto.QuartzJobDto;
import com.brycehan.boot.quartz.entity.dto.QuartzJobPageDto;
import com.brycehan.boot.quartz.entity.po.QuartzJob;
import com.brycehan.boot.quartz.entity.vo.QuartzJobVo;
import com.brycehan.boot.quartz.mapper.QuartzJobMapper;
import com.brycehan.boot.quartz.service.QuartzJobService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * quartz定时任务调度服务实现
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class QuartzJobServiceImpl extends BaseServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {

    private final Scheduler scheduler;

    /**
     * 启动项目时，初始化定时器
     *
     * @throws SchedulerException 初始化异常
     */
    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.clear();
        List<QuartzJob> quartzJobs = baseMapper.selectList(null);

        for (QuartzJob quartzJob : quartzJobs) {
            QuartzUtils.createScheduleJob(scheduler, quartzJob);
        }
    }

    @Override
    public void save(QuartzJobDto quartzJobDto) {
        QuartzJob quartzJob = QuartzJobConvert.INSTANCE.convert(quartzJobDto);
        quartzJob.setId(IdGenerator.nextId());

        if(baseMapper.insert(quartzJob) > 0) {
            QuartzUtils.createScheduleJob(scheduler, quartzJob);
        }
    }

    @Override
    public void update(QuartzJobDto quartzJobDto) {
        QuartzJob quartzJob = QuartzJobConvert.INSTANCE.convert(quartzJobDto);

        // 更新定时任务
        if (baseMapper.updateById(quartzJob) > 0) {
            QuartzJob entity = getById(quartzJobDto.getId());
            QuartzUtils.updateSchedulerJob(scheduler, entity);
        }
    }

    @Override
    @Transactional
    public void delete(IdsDto idsDto) {
        for (Long id: idsDto.getIds()) {
            QuartzJob quartzJob = getById(id);
            if(removeById(id)) {
                // 删除定时任务
                QuartzUtils.deleteSchedulerJob(scheduler, quartzJob);
            }
        }
    }

    @Override
    public PageResult<QuartzJobVo> page(QuartzJobPageDto quartzJobPageDto) {
        IPage<QuartzJob> page = baseMapper.selectPage(quartzJobPageDto.toPage(), getWrapper(quartzJobPageDto));
        return PageResult.of(QuartzJobConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    /**
     * 封装查询条件
     *
     * @param quartzJobPageDto quartz定时任务调度分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<QuartzJob> getWrapper(QuartzJobPageDto quartzJobPageDto){

        LambdaQueryWrapper<QuartzJob> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(quartzJobPageDto.getJobName()), QuartzJob::getJobName, quartzJobPageDto.getJobName());
        wrapper.eq(quartzJobPageDto.getJobGroup() != null, QuartzJob::getJobGroup, quartzJobPageDto.getJobGroup());
        wrapper.eq(Objects.nonNull(quartzJobPageDto.getStatus()), QuartzJob::getStatus, quartzJobPageDto.getStatus());

        return wrapper;
    }

    @Override
    public void export(QuartzJobPageDto quartzJobPageDto) {
        List<QuartzJob> quartzJobList = baseMapper.selectList(getWrapper(quartzJobPageDto));
        List<QuartzJobVo> quartzJobVoList = QuartzJobConvert.INSTANCE.convert(quartzJobList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(QuartzJobVo.class, "定时任务_".concat(today), "定时任务", quartzJobVoList);
    }

    @Override
    public void run(QuartzJobDto quartzJobDto) {
        QuartzJob quartzJob = getById(quartzJobDto.getId());
        if(quartzJob == null) {
            return;
        }

        QuartzUtils.runJob(scheduler, quartzJob);
    }

    @Override
    public void changeStatus(Long id, JobStatus status) {
        QuartzJob quartzJob = getById(id);
        if(quartzJob == null) {
            return;
        }

        // 更新数据
        quartzJob.setStatus(status);
        updateById(quartzJob);

        if(JobStatus.PAUSE == status) {
            QuartzUtils.pauseJob(scheduler, quartzJob);
        } else if (JobStatus.NORMAL == status) {
            QuartzUtils.resumeJob(scheduler, quartzJob);
        }
    }
}
