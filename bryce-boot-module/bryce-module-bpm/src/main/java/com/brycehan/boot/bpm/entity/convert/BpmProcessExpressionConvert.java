package com.brycehan.boot.bpm.entity.convert;

import com.brycehan.boot.bpm.entity.dto.BpmProcessExpressionDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessExpression;
import com.brycehan.boot.bpm.entity.vo.BpmProcessExpressionVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * 流程表达式转换器
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Mapper
public interface BpmProcessExpressionConvert {

    BpmProcessExpressionConvert INSTANCE = Mappers.getMapper(BpmProcessExpressionConvert.class);

    BpmProcessExpression convert(BpmProcessExpressionDto bpmProcessExpressionDto);

    BpmProcessExpressionVo convert(BpmProcessExpression bpmProcessExpression);

    List<BpmProcessExpressionVo> convert(List<BpmProcessExpression> bpmProcessExpressionList);

}