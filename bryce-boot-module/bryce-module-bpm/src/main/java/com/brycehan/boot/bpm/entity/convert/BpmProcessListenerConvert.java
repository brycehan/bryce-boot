package com.brycehan.boot.bpm.entity.convert;

import com.brycehan.boot.bpm.entity.dto.BpmProcessListenerDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessListener;
import com.brycehan.boot.bpm.entity.vo.BpmProcessListenerVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 流程监听器转换器
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Mapper
public interface BpmProcessListenerConvert {

    BpmProcessListenerConvert INSTANCE = Mappers.getMapper(BpmProcessListenerConvert.class);

    BpmProcessListener convert(BpmProcessListenerDto bpmProcessListenerDto);

    BpmProcessListenerVo convert(BpmProcessListener bpmProcessListener);

    List<BpmProcessListenerVo> convert(List<BpmProcessListener> bpmProcessListenerList);

}