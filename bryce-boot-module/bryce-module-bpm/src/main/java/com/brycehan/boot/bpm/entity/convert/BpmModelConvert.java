package com.brycehan.boot.bpm.entity.convert;

import cn.hutool.core.bean.BeanUtil;
import com.brycehan.boot.bpm.entity.dto.BpmModelDto;
import com.brycehan.boot.bpm.entity.dto.BpmModelMetaInfoDto;
import com.brycehan.boot.common.util.JsonUtils;
import org.flowable.engine.repository.Model;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

/**
 * 流程定义信息转换器
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Mapper
public interface BpmModelConvert {

    BpmModelConvert INSTANCE = Mappers.getMapper(BpmModelConvert.class);

    default void copyToModel(Model model, BpmModelDto bpmModelDto){
        model.setName(bpmModelDto.getName());
        model.setKey(bpmModelDto.getKey());
        model.setCategory(bpmModelDto.getCategory());
        model.setMetaInfo(JsonUtils.writeValueAsString(BeanUtil.toBean(bpmModelDto, BpmModelMetaInfoDto.class)));
    }

    /**
     * 解析流程模型元数据
     *
     * @param model 流程定义
     * @return BpmModelMetaInfoDto
     */
    default BpmModelMetaInfoDto parseMetaInfo(Model model) {
        BpmModelMetaInfoDto bpmModelMetaInfoDto = JsonUtils.readValue(model.getMetaInfo(), BpmModelMetaInfoDto.class);
        if (bpmModelMetaInfoDto == null) {
            return null;
        }
        if (bpmModelMetaInfoDto.getManagerUserIds() == null) {
            bpmModelMetaInfoDto.setManagerUserIds(Collections.emptyList());
        }
        if (bpmModelMetaInfoDto.getStartUserIds() == null) {
            bpmModelMetaInfoDto.setStartUserIds(Collections.emptyList());
        }
        if (bpmModelMetaInfoDto.getSort() == null) {
            bpmModelMetaInfoDto.setSort(model.getCreateTime().getTime());
        }
        return bpmModelMetaInfoDto;
    }
}