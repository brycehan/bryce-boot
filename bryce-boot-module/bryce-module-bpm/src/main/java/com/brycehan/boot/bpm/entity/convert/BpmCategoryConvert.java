package com.brycehan.boot.bpm.entity.convert;

import com.brycehan.boot.bpm.entity.dto.BpmCategoryDto;
import com.brycehan.boot.bpm.entity.po.BpmCategory;
import com.brycehan.boot.bpm.entity.vo.BpmCategoryVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 流程分类转换器
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Mapper
public interface BpmCategoryConvert {

    BpmCategoryConvert INSTANCE = Mappers.getMapper(BpmCategoryConvert.class);

    BpmCategory convert(BpmCategoryDto bpmCategoryDto);

    BpmCategoryVo convert(BpmCategory bpmCategory);

    List<BpmCategoryVo> convert(List<BpmCategory> bpmCategoryList);

}