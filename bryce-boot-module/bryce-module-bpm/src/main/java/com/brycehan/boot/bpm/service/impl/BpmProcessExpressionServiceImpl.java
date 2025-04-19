package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.BpmResponseStatus;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.bpm.entity.convert.BpmProcessExpressionConvert;
import com.brycehan.boot.bpm.entity.dto.BpmProcessExpressionDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessExpressionPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessExpression;
import com.brycehan.boot.bpm.entity.vo.BpmProcessExpressionVo;
import com.brycehan.boot.bpm.service.BpmProcessExpressionService;
import com.brycehan.boot.bpm.mapper.BpmProcessExpressionMapper;
import org.apache.commons.lang3.StringUtils;
import java.util.Objects;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import lombok.extern.slf4j.Slf4j;


/**
 * 流程表达式服务实现
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmProcessExpressionServiceImpl extends BaseServiceImpl<BpmProcessExpressionMapper, BpmProcessExpression> implements BpmProcessExpressionService {

    /**
     * 添加流程表达式
     *
     * @param bpmProcessExpressionDto 流程表达式Dto
     */
    public void save(BpmProcessExpressionDto bpmProcessExpressionDto) {
        BpmProcessExpression bpmProcessExpression = BpmProcessExpressionConvert.INSTANCE.convert(bpmProcessExpressionDto);
        bpmProcessExpression.setId(IdGenerator.nextId());
        baseMapper.insert(bpmProcessExpression);
    }

    /**
     * 更新流程表达式
     *
     * @param bpmProcessExpressionDto 流程表达式Dto
     */
    public void update(BpmProcessExpressionDto bpmProcessExpressionDto) {
        // 校验流程表达式是否存在
        validateProcessExpressionExists(bpmProcessExpressionDto.getId());
        // 更新流程表达式
        BpmProcessExpression bpmProcessExpression = BpmProcessExpressionConvert.INSTANCE.convert(bpmProcessExpressionDto);
        baseMapper.updateById(bpmProcessExpression);
    }

    @Override
    public PageResult<BpmProcessExpressionVo> page(BpmProcessExpressionPageDto bpmProcessExpressionPageDto) {
        IPage<BpmProcessExpression> page = baseMapper.selectPage(bpmProcessExpressionPageDto.toPage(), getWrapper(bpmProcessExpressionPageDto));
        return PageResult.of(BpmProcessExpressionConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    /**
     * 封装查询条件
     *
     * @param bpmProcessExpressionPageDto 流程表达式分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<BpmProcessExpression> getWrapper(BpmProcessExpressionPageDto bpmProcessExpressionPageDto){
        LambdaQueryWrapper<BpmProcessExpression> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(bpmProcessExpressionPageDto.getStatus()), BpmProcessExpression::getStatus, bpmProcessExpressionPageDto.getStatus());
        wrapper.eq(Objects.nonNull(bpmProcessExpressionPageDto.getTenantId()), BpmProcessExpression::getTenantId, bpmProcessExpressionPageDto.getTenantId());

        if(bpmProcessExpressionPageDto.getCreatedTimeStart() != null && bpmProcessExpressionPageDto.getCreatedTimeEnd() != null) {
            wrapper.between(BpmProcessExpression::getCreatedTime, bpmProcessExpressionPageDto.getCreatedTimeStart(), bpmProcessExpressionPageDto.getCreatedTimeEnd());
        } else if(bpmProcessExpressionPageDto.getCreatedTimeStart() != null) {
            wrapper.ge(BpmProcessExpression::getCreatedTime, bpmProcessExpressionPageDto.getCreatedTimeStart());
        }else if(bpmProcessExpressionPageDto.getCreatedTimeEnd() != null) {
            wrapper.ge(BpmProcessExpression::getCreatedTime, bpmProcessExpressionPageDto.getCreatedTimeEnd());
        }

        wrapper.like(StringUtils.isNotEmpty(bpmProcessExpressionPageDto.getName()), BpmProcessExpression::getName, bpmProcessExpressionPageDto.getName());
        return wrapper;
    }

    @Override
    public void export(BpmProcessExpressionPageDto bpmProcessExpressionPageDto) {
        List<BpmProcessExpression> bpmProcessExpressionList = baseMapper.selectList(getWrapper(bpmProcessExpressionPageDto));
        List<BpmProcessExpressionVo> bpmProcessExpressionVoList = BpmProcessExpressionConvert.INSTANCE.convert(bpmProcessExpressionList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(BpmProcessExpressionVo.class, "流程表达式_".concat(today), "流程表达式", bpmProcessExpressionVoList);
    }

    /**
     * 校验流程表达式是否存在
     *
     * @param id 流程表达式ID
     * @throws ServerException 流程表达式不存在
     */
    private void validateProcessExpressionExists(Long id) {
        if (!exists(id)) {
            throw ServerException.of(BpmResponseStatus.PROCESS_EXPRESSION_NOT_EXISTS);
        }
    }

}
