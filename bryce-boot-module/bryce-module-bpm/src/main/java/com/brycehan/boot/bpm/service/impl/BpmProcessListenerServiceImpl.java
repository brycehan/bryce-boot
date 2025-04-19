package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import java.util.Date;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.bpm.common.type.BpmProcessListenerTypeEnum;
import com.brycehan.boot.bpm.common.type.BpmProcessListenerValueTypeEnum;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.BpmResponseStatus;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.bpm.entity.convert.BpmProcessListenerConvert;
import com.brycehan.boot.bpm.entity.dto.BpmProcessListenerDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessListenerPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessListener;
import com.brycehan.boot.bpm.entity.vo.BpmProcessListenerVo;
import com.brycehan.boot.bpm.service.BpmProcessListenerService;
import com.brycehan.boot.bpm.mapper.BpmProcessListenerMapper;
import org.apache.commons.lang3.StringUtils;
import java.util.Objects;

import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import lombok.extern.slf4j.Slf4j;


/**
 * 流程监听器服务实现
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmProcessListenerServiceImpl extends BaseServiceImpl<BpmProcessListenerMapper, BpmProcessListener> implements BpmProcessListenerService {

    /**
     * 添加流程监听器
     *
     * @param bpmProcessListenerDto 流程监听器Dto
     */
    public void save(BpmProcessListenerDto bpmProcessListenerDto) {
        // 校验
        validateCreateProcessListenerValue(bpmProcessListenerDto);

        // 插入
        BpmProcessListener bpmProcessListener = BpmProcessListenerConvert.INSTANCE.convert(bpmProcessListenerDto);
        bpmProcessListener.setId(IdGenerator.nextId());
        baseMapper.insert(bpmProcessListener);
    }

    /**
     * 更新流程监听器
     *
     * @param bpmProcessListenerDto 流程监听器Dto
     */
    public void update(BpmProcessListenerDto bpmProcessListenerDto) {
        // 校验存在
        validateProcessListenerExists(bpmProcessListenerDto.getId());
        // 校验值
        validateCreateProcessListenerValue(bpmProcessListenerDto);

        // 更新
        BpmProcessListener bpmProcessListener = BpmProcessListenerConvert.INSTANCE.convert(bpmProcessListenerDto);
        baseMapper.updateById(bpmProcessListener);
    }

    /**
     * 校验流程监听器是否存在
     *
     * @param id 流程监听器ID
     */
    private void validateProcessListenerExists(Long id) {
        LambdaQueryWrapper<BpmProcessListener> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BpmProcessListener::getId, id);
        if (!baseMapper.exists(queryWrapper)) {
            throw ServerException.of(BpmResponseStatus.PROCESS_LISTENER_NOT_EXISTS);
        }
    }

    /**
     * 校验流程监听器
     *
     * @param bpmProcessListenerDto 流程监听器Dto
     */
    private void validateCreateProcessListenerValue(BpmProcessListenerDto bpmProcessListenerDto) {
        // class 类型
        if (BpmProcessListenerValueTypeEnum.CLASS.getType().equals(bpmProcessListenerDto.getValueType())) {
            try {
                Class<?> clazz = Class.forName(bpmProcessListenerDto.getValue());
                if (bpmProcessListenerDto.getType().equals(BpmProcessListenerTypeEnum.EXECUTION.getType()) && !JavaDelegate.class.isAssignableFrom(clazz)) {
                    throw ServerException.of(BpmResponseStatus.PROCESS_LISTENER_CLASS_IMPLEMENTS_ERROR, bpmProcessListenerDto.getValue(), JavaDelegate.class.getName());
                } else if (bpmProcessListenerDto.getType().equals(BpmProcessListenerTypeEnum.TASK.getType()) && !TaskListener.class.isAssignableFrom(clazz)) {
                    throw ServerException.of(BpmResponseStatus.PROCESS_LISTENER_CLASS_IMPLEMENTS_ERROR, bpmProcessListenerDto.getValue(), TaskListener.class.getName());
                }
            } catch (ClassNotFoundException e) {
                throw ServerException.of(BpmResponseStatus.PROCESS_LISTENER_CLASS_NOT_FOUND, bpmProcessListenerDto.getValue());
            }
            return;
        }
        // 表达式
        if (!StrUtil.startWith(bpmProcessListenerDto.getValue(), "${") || !StrUtil.endWith(bpmProcessListenerDto.getValue(), "}")) {
            throw ServerException.of(BpmResponseStatus.PROCESS_LISTENER_EXPRESSION_INVALID, bpmProcessListenerDto.getValue());
        }
    }

    @Override
    public PageResult<BpmProcessListenerVo> page(BpmProcessListenerPageDto bpmProcessListenerPageDto) {
        IPage<BpmProcessListener> page = baseMapper.selectPage(bpmProcessListenerPageDto.toPage(), getWrapper(bpmProcessListenerPageDto));
        return PageResult.of(BpmProcessListenerConvert.INSTANCE.convert(page.getRecords()), page.getTotal());
    }

    /**
     * 封装查询条件
     *
     * @param bpmProcessListenerPageDto 流程监听器分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<BpmProcessListener> getWrapper(BpmProcessListenerPageDto bpmProcessListenerPageDto){
        LambdaQueryWrapper<BpmProcessListener> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(bpmProcessListenerPageDto.getStatus()), BpmProcessListener::getStatus, bpmProcessListenerPageDto.getStatus());
        wrapper.eq(StringUtils.isNotEmpty(bpmProcessListenerPageDto.getType()), BpmProcessListener::getType, bpmProcessListenerPageDto.getType());
        wrapper.eq(Objects.nonNull(bpmProcessListenerPageDto.getTenantId()), BpmProcessListener::getTenantId, bpmProcessListenerPageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(bpmProcessListenerPageDto.getName()), BpmProcessListener::getName, bpmProcessListenerPageDto.getName());
        return wrapper;
    }

    @Override
    public void export(BpmProcessListenerPageDto bpmProcessListenerPageDto) {
        List<BpmProcessListener> bpmProcessListenerList = baseMapper.selectList(getWrapper(bpmProcessListenerPageDto));
        List<BpmProcessListenerVo> bpmProcessListenerVoList = BpmProcessListenerConvert.INSTANCE.convert(bpmProcessListenerList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(BpmProcessListenerVo.class, "流程监听器_".concat(today), "流程监听器", bpmProcessListenerVoList);
    }

}
