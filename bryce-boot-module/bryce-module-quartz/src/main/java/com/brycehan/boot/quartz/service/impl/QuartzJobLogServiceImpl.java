package com.brycehan.boot.quartz.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.quartz.entity.convert.QuartzJobLogConvert;
import com.brycehan.boot.quartz.entity.dto.QuartzJobLogPageDto;
import com.brycehan.boot.quartz.entity.po.QuartzJobLog;
import com.brycehan.boot.quartz.entity.vo.QuartzJobLogVo;
import com.brycehan.boot.quartz.mapper.QuartzJobLogMapper;
import com.brycehan.boot.quartz.service.QuartzJobLogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * quartz定时任务调度日志服务实现
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class QuartzJobLogServiceImpl extends BaseServiceImpl<QuartzJobLogMapper, QuartzJobLog> implements QuartzJobLogService {

    @Override
    public PageResult<QuartzJobLogVo> page(QuartzJobLogPageDto quartzJobLogPageDto) {
        IPage<QuartzJobLog> page = baseMapper.selectPage(quartzJobLogPageDto.toPage(), getWrapper(quartzJobLogPageDto));
        return PageResult.of(QuartzJobLogConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    /**
     * 封装查询条件
     *
     * @param quartzJobLogPageDto quartz定时任务调度日志分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<QuartzJobLog> getWrapper(QuartzJobLogPageDto quartzJobLogPageDto){
        LambdaQueryWrapper<QuartzJobLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(quartzJobLogPageDto.getJobName()), QuartzJobLog::getJobName, quartzJobLogPageDto.getJobName());
        wrapper.eq(quartzJobLogPageDto.getJobGroup() != null, QuartzJobLog::getJobGroup, quartzJobLogPageDto.getJobGroup());
        return wrapper;
    }

    @Override
    public void export(QuartzJobLogPageDto quartzJobLogPageDto) {
        List<QuartzJobLog> quartzJobLogList = baseMapper.selectList(getWrapper(quartzJobLogPageDto));
        List<QuartzJobLogVo> quartzJobLogVoList = QuartzJobLogConvert.INSTANCE.convert(quartzJobLogList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(QuartzJobLogVo.class, "任务调度日志_".concat(today), "任务调度日志", quartzJobLogVoList);
    }

    @Override
    public void cleanJobLog() {
        baseMapper.cleanJobLog();
    }

}
