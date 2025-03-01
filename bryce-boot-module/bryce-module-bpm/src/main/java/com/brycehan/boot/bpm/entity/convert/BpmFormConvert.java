package com.brycehan.boot.bpm.entity.convert;

import com.brycehan.boot.bpm.entity.dto.BpmFormDto;
import com.brycehan.boot.bpm.entity.po.BpmForm;
import com.brycehan.boot.bpm.entity.vo.BpmFormVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 表单定义转换器
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BpmFormConvert {

    BpmFormConvert INSTANCE = Mappers.getMapper(BpmFormConvert.class);

    BpmForm convert(BpmFormDto bpmFormDto);

    BpmFormVo convert(BpmForm bpmForm);

    List<BpmFormVo> convert(List<BpmForm> bpmFormList);

}