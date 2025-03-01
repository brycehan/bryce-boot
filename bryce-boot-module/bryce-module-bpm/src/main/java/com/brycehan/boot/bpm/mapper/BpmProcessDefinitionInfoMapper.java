package com.brycehan.boot.bpm.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;

/**
* 流程定义信息Mapper接口
*
* @author Bryce Han
* @since 2025/02/24
*/
@Mapper
public interface BpmProcessDefinitionInfoMapper extends BryceBaseMapper<BpmProcessDefinitionInfo> {

}
