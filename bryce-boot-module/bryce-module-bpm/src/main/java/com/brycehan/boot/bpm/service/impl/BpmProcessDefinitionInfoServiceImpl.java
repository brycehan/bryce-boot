package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.bpm.entity.convert.BpmProcessDefinitionInfoConvert;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoDto;
import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionInfoPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionInfoVo;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionInfoService;
import com.brycehan.boot.bpm.mapper.BpmProcessDefinitionInfoMapper;
import java.util.Objects;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import lombok.extern.slf4j.Slf4j;


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

    /**
     * 添加流程定义信息
     *
     * @param bpmProcessDefinitionInfoDto 流程定义信息Dto
     */
    public void save(BpmProcessDefinitionInfoDto bpmProcessDefinitionInfoDto) {
        BpmProcessDefinitionInfo bpmProcessDefinitionInfo = BpmProcessDefinitionInfoConvert.INSTANCE.convert(bpmProcessDefinitionInfoDto);
        bpmProcessDefinitionInfo.setId(IdGenerator.nextId());
        baseMapper.insert(bpmProcessDefinitionInfo);
    }

    /**
     * 更新流程定义信息
     *
     * @param bpmProcessDefinitionInfoDto 流程定义信息Dto
     */
    public void update(BpmProcessDefinitionInfoDto bpmProcessDefinitionInfoDto) {
        BpmProcessDefinitionInfo bpmProcessDefinitionInfo = BpmProcessDefinitionInfoConvert.INSTANCE.convert(bpmProcessDefinitionInfoDto);
        baseMapper.updateById(bpmProcessDefinitionInfo);
    }

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

}
