package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.bpm.entity.convert.BpmProcessDefinitionInfoConvert;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionInfoVo;
import com.brycehan.boot.bpm.mapper.BpmProcessDefinitionInfoMapper;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionInfoService;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 流程定义信息服务实现
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmProcessDefinitionInfoServiceImpl extends BaseServiceImpl<BpmProcessDefinitionInfoMapper, BpmProcessDefinitionInfo> implements BpmProcessDefinitionInfoService {

    @Override
    public PageResult<BpmProcessDefinitionInfoVo> page(BpmProcessDefinitionInfoPageDto bpmProcessDefinitionInfoPageDto) {
        IPage<BpmProcessDefinitionInfo> page = baseMapper.selectPage(bpmProcessDefinitionInfoPageDto.toPage(), getWrapper(bpmProcessDefinitionInfoPageDto));
        return new PageResult<>(page.getTotal(), BpmProcessDefinitionInfoConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param bpmProcessDefinitionInfoPageDto 流程定义信息分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<BpmProcessDefinitionInfo> getWrapper(BpmProcessDefinitionInfoPageDto bpmProcessDefinitionInfoPageDto){
        LambdaQueryWrapper<BpmProcessDefinitionInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(bpmProcessDefinitionInfoPageDto.getTenantId()), BpmProcessDefinitionInfo::getTenantId, bpmProcessDefinitionInfoPageDto.getTenantId());
        return wrapper;
    }

    @Override
    public void export(BpmProcessDefinitionInfoPageDto bpmProcessDefinitionInfoPageDto) {
        List<BpmProcessDefinitionInfo> bpmProcessDefinitionInfoList = baseMapper.selectList(getWrapper(bpmProcessDefinitionInfoPageDto));
        List<BpmProcessDefinitionInfoVo> bpmProcessDefinitionInfoVoList = BpmProcessDefinitionInfoConvert.INSTANCE.convert(bpmProcessDefinitionInfoList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(BpmProcessDefinitionInfoVo.class, "流程定义信息_".concat(today), "流程定义信息", bpmProcessDefinitionInfoVoList);
    }

    @Override
    public Map<String, BpmProcessDefinitionInfo> getProcessDefinitionInfoMap(List<String> processDefinitionIds) {
        if (CollUtil.isEmpty(processDefinitionIds)) {
            return Map.of();
        }

        LambdaQueryWrapper<BpmProcessDefinitionInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BpmProcessDefinitionInfo::getProcessDefinitionId, processDefinitionIds);
        List<BpmProcessDefinitionInfo> bpmProcessDefinitionInfos = baseMapper.selectList(queryWrapper);
        return bpmProcessDefinitionInfos.stream().collect(Collectors.toMap(BpmProcessDefinitionInfo::getProcessDefinitionId, bpmProcessDefinitionInfo -> bpmProcessDefinitionInfo));
    }
}
