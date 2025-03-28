package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.dto.SysDeptDto;
import com.brycehan.boot.system.entity.po.SysDept;
import com.brycehan.boot.system.entity.vo.SysDeptSimpleVo;
import com.brycehan.boot.system.entity.vo.SysDeptVo;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统部门服务
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
public interface SysDeptService extends BaseService<SysDept> {

    /**
     * 添加系统部门
     *
     * @param sysDeptDto 系统部门Dto
     */
    void save(SysDeptDto sysDeptDto);

    /**
     * 更新系统部门
     *
     * @param sysDeptDto 系统部门Dto
     */
    void update(SysDeptDto sysDeptDto);

    /**
     * 列表查询
     *
     * @param sysDeptDto 查询参数
     * @return 部门列表
     */
    List<SysDeptVo> list(SysDeptDto sysDeptDto);

    /**
     * 列表查询，用于前端 select 选择
     *
     * @param sysDeptDto 查询参数
     * @return 部门列表
     */
    List<SysDeptSimpleVo> simpleList(SysDeptDto sysDeptDto);

    /**
     * 根据部门ID，获取子部门ID列表（包含本部门ID）
     *
     * @param id 部门ID
     * @return 子部门ID列表
     */
    List<Long> getSubDeptIds(Long id);

    /**
     * 根据部门ID，获取部门名称
     *
     * @param deptId 部门ID
     * @return 部门名称
     */
    String getDeptNameById(Long deptId);

    /**
     * 根据部门ID列表，获取部门名称列表
     *
     * @param deptIds 部门ID列表
     * @return 部门ID部门名称列表map
     */
    default Map<Long, String> getDeptNamesByIds(List<Long> deptIds) {
        if (CollectionUtils.isEmpty(deptIds)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysDept::getId, SysDept::getName);
        queryWrapper.in(SysDept::getId, deptIds);

        List<SysDept> sysDeptList = getBaseMapper().selectList(queryWrapper);

        if (CollectionUtils.isEmpty(sysDeptList)) {
            return new HashMap<>();
        }

        return sysDeptList.stream().collect(Collectors.toMap(SysDept::getId, SysDept::getName));
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门ID
     */
    void checkDeptDataScope(Long deptId);
}
