package com.brycehan.boot.system.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.brycehan.boot.api.system.BpmDeptApi;
import com.brycehan.boot.api.system.dto.BpmDeptDto;
import com.brycehan.boot.api.system.vo.BpmDeptVo;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.SystemResponseStatus;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.system.entity.po.SysOrg;
import com.brycehan.boot.system.service.SysOrgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 系统机构部门 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmDeptApiService implements BpmDeptApi {

    private final SysOrgService sysOrgService;

    @Override
    public BpmDeptVo getDept(Long id) {
        SysOrg sysOrg = sysOrgService.getById(id);
        return BeanUtil.copyProperties(sysOrg, BpmDeptVo.class);
    }

    @Override
    public List<BpmDeptVo> getDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return List.of();
        }
        List<SysOrg> sysOrgList = sysOrgService.listByIds(ids);
        return BeanUtil.copyToList(sysOrgList, BpmDeptVo.class);
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
                throw ServerException.of(SystemResponseStatus.ORG_NOT_FOUND);
            }
            if (!StatusType.ENABLE.getValue().equals(dept.getStatus())) {
                throw ServerException.of(SystemResponseStatus.ORG_NOT_ENABLE, dept.getName());
            }
        });
    }

    @Override
    public List<BpmDeptDto> getChildDeptList(Long id) {
        List<Long> subOrgIds = sysOrgService.getSubOrgIds(id);

        if (CollUtil.isEmpty(subOrgIds)) {
            return List.of();
        }

        List<SysOrg> sysOrgList = sysOrgService.listByIds(subOrgIds);
        return BeanUtil.copyToList(sysOrgList, BpmDeptDto.class);
    }
}
