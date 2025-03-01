package com.brycehan.boot.bpm.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.bpm.entity.po.BpmForm;

/**
* 表单定义Mapper接口
*
* @author Bryce Han
* @since 2025/02/23
*/
@Mapper
public interface BpmFormMapper extends BryceBaseMapper<BpmForm> {

}
