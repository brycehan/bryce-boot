package com.brycehan.boot.bpm.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.brycehan.boot.bpm.entity.po.BpmProcessExpression;

/**
* 流程表达式Mapper接口
*
* @author Bryce Han
* @since 2025/03/25
*/
@Mapper
public interface BpmProcessExpressionMapper extends BryceBaseMapper<BpmProcessExpression> {

}
