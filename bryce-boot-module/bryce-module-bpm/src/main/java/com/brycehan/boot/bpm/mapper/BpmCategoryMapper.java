package com.brycehan.boot.bpm.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.bpm.entity.po.BpmCategory;

/**
* 流程分类Mapper接口
*
* @author Bryce Han
* @since 2025/02/23
*/
@Mapper
public interface BpmCategoryMapper extends BryceBaseMapper<BpmCategory> {

}
