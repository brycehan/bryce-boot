package com.brycehan.boot.bpm.entity.convert;

import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceCopyDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessInstanceCopy;
import com.brycehan.boot.bpm.entity.vo.BpmProcessInstanceCopyVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 流程抄送转换器
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Mapper
public interface BpmProcessInstanceCopyConvert {

    BpmProcessInstanceCopyConvert INSTANCE = Mappers.getMapper(BpmProcessInstanceCopyConvert.class);

    BpmProcessInstanceCopy convert(BpmProcessInstanceCopyDto bpmProcessInstanceCopyDto);

    BpmProcessInstanceCopyVo convert(BpmProcessInstanceCopy bpmProcessInstanceCopy);

    List<BpmProcessInstanceCopyVo> convert(List<BpmProcessInstanceCopy> bpmProcessInstanceCopyList);

}