package com.brycehan.boot.api.system;

import com.brycehan.boot.api.system.dto.BpmDeptDto;
import com.brycehan.boot.api.system.vo.BpmDeptVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 部门 API 接口
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
public interface BpmDeptApi {

    /**
     * 获得部门信息
     *
     * @param id 部门编号
     * @return 部门信息
     */
    BpmDeptVo getDept(Long id);

    /**
     * 获得部门信息数组
     *
     * @param ids 部门编号数组
     * @return 部门信息数组
     */
    List<BpmDeptVo> getDeptList(Collection<Long> ids);

    /**
     * 校验部门们是否有效。如下情况，视为无效：
     * 1. 部门编号不存在
     * 2. 部门被禁用
     *
     * @param ids 角色编号数组
     */
    void validateDeptList(Collection<Long> ids);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<Long, BpmDeptVo> getDeptMap(Collection<Long> ids) {
        List<BpmDeptVo> list = getDeptList(ids);
        return list.stream().collect(Collectors.toMap(BpmDeptVo::getId, Function.identity()));
    }

    /**
     * 获得指定部门的所有子部门
     *
     * @param id 部门编号
     * @return 子部门列表
     */
    List<BpmDeptDto> getChildDeptList(Long id);

}
