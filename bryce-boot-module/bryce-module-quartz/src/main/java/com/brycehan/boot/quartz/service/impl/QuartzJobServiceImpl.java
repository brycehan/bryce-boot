package com.brycehan.boot.quartz.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.util.ExcelUtils;
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
import org.apache.commons.collections4.CollectionUtils;
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
        List<QuartzJob> quartzJobs = this.baseMapper.selectList(null);

        for (QuartzJob quartzJob : quartzJobs) {
            QuartzUtils.createScheduleJob(scheduler, quartzJob);
        }
    }

    @Override
    public void save(QuartzJobDto quartzJobDto) {
        QuartzJob quartzJob = QuartzJobConvert.INSTANCE.convert(quartzJobDto);
        quartzJob.setId(IdGenerator.nextId());

        if(this.baseMapper.insert(quartzJob) > 0) {
            QuartzUtils.createScheduleJob(scheduler, quartzJob);
        }
    }

    @Override
    public void update(QuartzJobDto quartzJobDto) {
        QuartzJob quartzJob = QuartzJobConvert.INSTANCE.convert(quartzJobDto);

        // 更新定时任务
        if (this.baseMapper.updateById(quartzJob) > 0) {
            QuartzJob entity = getById(quartzJobDto.getId());
            QuartzUtils.updateSchedulerJob(scheduler, entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(IdsDto idsDto) {
        // 过滤无效参数
        List<Long> ids = idsDto.getIds().stream()
                .filter(Objects::nonNull)
                .toList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        for (Long id: ids) {
            // 删除定时任务
            if(removeById(id)) {
                QuartzJob quartzJob = getById(id);
                QuartzUtils.deleteSchedulerJob(scheduler, quartzJob);
            }
        }
    }

    @Override
    public PageResult<QuartzJobVo> page(QuartzJobPageDto quartzJobPageDto) {
        IPage<QuartzJob> page = this.baseMapper.selectPage(quartzJobPageDto.toPage(), getWrapper(quartzJobPageDto));
        return new PageResult<>(page.getTotal(), QuartzJobConvert.INSTANCE.convert(page.getRecords()));
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
        List<QuartzJob> quartzJobList = this.baseMapper.selectList(getWrapper(quartzJobPageDto));
        List<QuartzJobVo> quartzJobVoList = QuartzJobConvert.INSTANCE.convert(quartzJobList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(QuartzJobVo.class, "quartz定时任务调度_".concat(today), "quartz定时任务调度", quartzJobVoList);
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
    public void changeStatus(QuartzJobDto quartzJobDto) {
        QuartzJob quartzJob = getById(quartzJobDto.getId());
        if(quartzJob == null) {
            return;
        }

        // 更新数据
        quartzJob.setStatus(quartzJobDto.getStatus());
        updateById(quartzJob);

        if(JobStatus.PAUSE == quartzJobDto.getStatus()) {
            QuartzUtils.pauseJob(scheduler, quartzJob);
        } else if (JobStatus.NORMAL == quartzJobDto.getStatus()) {
            QuartzUtils.resumeJob(scheduler, quartzJob);
        }
    }
}
