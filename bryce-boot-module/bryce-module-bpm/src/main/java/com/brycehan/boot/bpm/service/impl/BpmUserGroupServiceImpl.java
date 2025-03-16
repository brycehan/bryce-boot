package com.brycehan.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.BpmResponseStatus;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.bpm.entity.convert.BpmUserGroupConvert;
import com.brycehan.boot.bpm.entity.dto.BpmUserGroupDto;
import com.brycehan.boot.bpm.entity.dto.BpmUserGroupPageDto;
import com.brycehan.boot.bpm.entity.po.BpmUserGroup;
import com.brycehan.boot.bpm.entity.vo.BpmUserGroupVo;
import com.brycehan.boot.bpm.service.BpmUserGroupService;
import com.brycehan.boot.bpm.mapper.BpmUserGroupMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;


/**
 * 用户组服务实现
 *
 * @author Bryce Han
 * @since 2025/03/08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmUserGroupServiceImpl extends BaseServiceImpl<BpmUserGroupMapper, BpmUserGroup> implements BpmUserGroupService {

    /**
     * 添加用户组
     *
     * @param bpmUserGroupDto 用户组Dto
     */
    public void save(BpmUserGroupDto bpmUserGroupDto) {
        BpmUserGroup bpmUserGroup = BpmUserGroupConvert.INSTANCE.convert(bpmUserGroupDto);
        bpmUserGroup.setId(IdGenerator.nextId());
        baseMapper.insert(bpmUserGroup);
    }

    /**
     * 更新用户组
     *
     * @param bpmUserGroupDto 用户组Dto
     */
    public void update(BpmUserGroupDto bpmUserGroupDto) {
        BpmUserGroup bpmUserGroup = BpmUserGroupConvert.INSTANCE.convert(bpmUserGroupDto);
        baseMapper.updateById(bpmUserGroup);
    }

    @Override
    public PageResult<BpmUserGroupVo> page(BpmUserGroupPageDto bpmUserGroupPageDto) {
        IPage<BpmUserGroup> page = baseMapper.selectPage(bpmUserGroupPageDto.toPage(), getWrapper(bpmUserGroupPageDto));
        return new PageResult<>(page.getTotal(), BpmUserGroupConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param bpmUserGroupPageDto 用户组分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<BpmUserGroup> getWrapper(BpmUserGroupPageDto bpmUserGroupPageDto){
        LambdaQueryWrapper<BpmUserGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(bpmUserGroupPageDto.getStatus()), BpmUserGroup::getStatus, bpmUserGroupPageDto.getStatus());
        wrapper.eq(Objects.nonNull(bpmUserGroupPageDto.getTenantId()), BpmUserGroup::getTenantId, bpmUserGroupPageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(bpmUserGroupPageDto.getName()), BpmUserGroup::getName, bpmUserGroupPageDto.getName());

        addTimeRangeCondition(wrapper, BpmUserGroup::getCreatedTime, bpmUserGroupPageDto.getCreatedTimeStart(), bpmUserGroupPageDto.getCreatedTimeEnd());

        return wrapper;
    }

    @Override
    public void export(BpmUserGroupPageDto bpmUserGroupPageDto) {
        List<BpmUserGroup> bpmUserGroupList = baseMapper.selectList(getWrapper(bpmUserGroupPageDto));
        List<BpmUserGroupVo> bpmUserGroupVoList = BpmUserGroupConvert.INSTANCE.convert(bpmUserGroupList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(BpmUserGroupVo.class, "用户组_".concat(today), "用户组", bpmUserGroupVoList);
    }

    @Override
    public List<BpmUserGroup> simpleList(BpmUserGroupPageDto bpmUserGroupPageDto) {
        LambdaQueryWrapper<BpmUserGroup> queryWrapper = getWrapper(bpmUserGroupPageDto);
        queryWrapper.select(BpmUserGroup::getId, BpmUserGroup::getName);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void validUserGroups(Collection<Long> groupIds) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        // 获得用户组信息
        List<BpmUserGroup> userGroups = baseMapper.selectByIds(groupIds);
        Map<Long, BpmUserGroup> userGroupMap = userGroups.stream().collect(Collectors.toMap(BpmUserGroup::getId, Function.identity()));
        // 校验
        groupIds.forEach(groupId -> {
            BpmUserGroup userGroup = userGroupMap.get(groupId);
            if (userGroup == null) {
                throw ServerException.of(BpmResponseStatus.USER_GROUP_NOT_EXISTS);
            }
            if (!StatusType.ENABLE.equals(userGroup.getStatus())) {
                throw ServerException.of(BpmResponseStatus.USER_GROUP_IS_DISABLE, userGroup.getName());
            }
        });
    }

}
