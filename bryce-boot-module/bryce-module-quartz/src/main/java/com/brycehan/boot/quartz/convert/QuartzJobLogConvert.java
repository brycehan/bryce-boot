package com.brycehan.boot.quartz.convert;

import com.brycehan.boot.quartz.dto.QuartzJobLogDto;
import com.brycehan.boot.quartz.entity.QuartzJobLog;
import com.brycehan.boot.quartz.vo.QuartzJobLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * quartz 定时任务调度日志转换器
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Mapper
public interface QuartzJobLogConvert {

    QuartzJobLogConvert INSTANCE = Mappers.getMapper(QuartzJobLogConvert.class);

    QuartzJobLog convert(QuartzJobLogDto quartzJobLogDto);

    QuartzJobLogVo convert(QuartzJobLog quartzJobLog);

    List<QuartzJobLogVo> convert(List<QuartzJobLog> quartzJobLogList);

}