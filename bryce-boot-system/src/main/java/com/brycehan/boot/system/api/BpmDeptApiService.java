package com.brycehan.boot.system.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.brycehan.boot.api.system.BpmDeptApi;
import com.brycehan.boot.api.system.dto.BpmDeptDto;
import com.brycehan.boot.api.system.vo.BpmDeptVo;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.SystemResponseStatus;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.system.entity.po.SysDept;
import com.brycehan.boot.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 系统部门部门 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmDeptApiService implements BpmDeptApi {

    private final SysDeptService sysDeptService;

    @Override
    public BpmDeptVo getDept(Long id) {
        SysDept sysDept = sysDeptService.getById(id);
        return BeanUtil.copyProperties(sysDept, BpmDeptVo.class);
    }

    @Override
    public List<BpmDeptVo> getDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return List.of();
        }
        List<SysDept> sysDeptList = sysDeptService.listByIds(ids);
        return BeanUtil.copyToList(sysDeptList, BpmDeptVo.class);
    }

    @Override
    public void validateDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        Map<Long, BpmDeptVo> deptMap = getDeptMap(ids);
        // 校验
        ids.forEach(id -> {
            BpmDeptVo dept = deptMap.get(id);
            if (dept == null) {
                throw ServerException.of(SystemResponseStatus.DEPT_NOT_FOUND);
            }
            if (!StatusType.ENABLE.getValue().equals(dept.getStatus())) {
                throw ServerException.of(SystemResponseStatus.DEPT_NOT_ENABLE, dept.getName());
            }
        });
    }

    @Override
    public List<BpmDeptDto> getChildDeptList(Long id) {
        List<Long> subDeptIds = sysDeptService.getSubDeptIds(id);

        if (CollUtil.isEmpty(subDeptIds)) {
            return List.of();
        }

        List<SysDept> sysDeptList = sysDeptService.listByIds(subDeptIds);
        return BeanUtil.copyToList(sysDeptList, BpmDeptDto.class);
    }
}
