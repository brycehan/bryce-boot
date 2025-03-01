package com.brycehan.boot.bpm.entity.convert;

import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 流程定义信息转换器
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BpmProcessDefinitionInfoConvert {

    BpmProcessDefinitionInfoConvert INSTANCE = Mappers.getMapper(BpmProcessDefinitionInfoConvert.class);

    BpmProcessDefinitionInfo convert(BpmProcessDefinitionInfoDto bpmProcessDefinitionInfoDto);

    BpmProcessDefinitionInfoVo convert(BpmProcessDefinitionInfo bpmProcessDefinitionInfo);

    List<BpmProcessDefinitionInfoVo> convert(List<BpmProcessDefinitionInfo> bpmProcessDefinitionInfoList);

}