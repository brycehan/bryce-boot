package com.brycehan.boot.bpm.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.bpm.entity.po.BpmProcessInstanceCopy;

/**
* 流程抄送Mapper接口
*
* @author Bryce Han
* @since 2025/03/25
*/
@Mapper
public interface BpmProcessInstanceCopyMapper extends BryceBaseMapper<BpmProcessInstanceCopy> {

}
