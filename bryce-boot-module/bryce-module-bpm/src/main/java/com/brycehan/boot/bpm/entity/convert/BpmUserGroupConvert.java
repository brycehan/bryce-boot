package com.brycehan.boot.bpm.entity.convert;

import com.brycehan.boot.bpm.entity.dto.BpmUserGroupDto;
import com.brycehan.boot.bpm.entity.po.BpmUserGroup;
import com.brycehan.boot.bpm.entity.vo.BpmUserGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 用户组转换器
 *
 * @author Bryce Han
 * @since 2025/03/08
 */
@Mapper
public interface BpmUserGroupConvert {

    BpmUserGroupConvert INSTANCE = Mappers.getMapper(BpmUserGroupConvert.class);

    BpmUserGroup convert(BpmUserGroupDto bpmUserGroupDto);

    BpmUserGroupVo convert(BpmUserGroup bpmUserGroup);

    List<BpmUserGroupVo> convert(List<BpmUserGroup> bpmUserGroupList);

}