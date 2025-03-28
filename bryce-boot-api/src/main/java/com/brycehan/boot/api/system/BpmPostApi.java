package com.brycehan.boot.api.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.brycehan.boot.api.system.vo.BpmPostVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Bpm 岗位 API
 *
 * @since 2025/3/28
 * @author Bryce Han
 */
public interface BpmPostApi {

    /**
     * 校验岗位们是否有效。
     * <br>
     * 如下情况，视为无效：
     * <br>
     * 1. 岗位编号不存在
     * <br>
     * 2. 岗位被禁用
     *
     * @param ids 岗位编号数组
     */
    void validatePostList(Collection<Long> ids);

    /**
     * 根据岗位编号数组，获得岗位列表
     *
     * @param ids 岗位编号数组
     * @return 岗位列表
     */
    List<BpmPostVo> getPostList(Collection<Long> ids);

    /**
     * 根据岗位编号数组，获得岗位 Map
     *
     * @param ids 岗位编号数组
     * @return 岗位 Map
     */
    default Map<Long, BpmPostVo> getPostMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return MapUtil.empty();
        }

        List<BpmPostVo> list = getPostList(ids);
        return list.stream().collect(Collectors.toMap(BpmPostVo::getId, post -> post));
    }

}
