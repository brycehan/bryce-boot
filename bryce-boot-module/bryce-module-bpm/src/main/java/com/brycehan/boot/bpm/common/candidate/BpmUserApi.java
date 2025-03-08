package com.brycehan.boot.bpm.common.candidate;

import java.util.List;

/**
 * BPM 用户 API
 *
 * @author Bryce Han
 * @since 2025/3/4
 */
public interface BpmUserApi {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    BpmUserVo getUser(Long userId);

    /**
     * 根据用户ID列表获取用户信息
     *
     * @param userIds 用户ID列表
     * @return 用户信息列表
     */
    List<BpmUserVo> getUsers(List<Long> userIds);

    /**
     * 根据机构ID列表获取用户信息
     *
     * @param orgIds 机构ID列表
     * @return 用户信息列表
     */
    List<BpmUserVo> getUsersByOrgIds(List<Long> orgIds);

    /**
     * 根据岗位ID列表获取用户信息
     *
     * @param postIds 岗位ID列表
     * @return 用户信息列表
     */
    List<BpmUserVo> getUsersByPostIds(List<Long> postIds);

    /**
     * 验证用户是否有效（用户编号不存在/用户被停用）
     *
     * @param userId 用户ID
     */
    default void validateUser(Long userId) {
        validateUsers(List.of(userId));
    }

    /**
     * 验证用户是否有效（用户编号不存在/用户被停用）
     *
     * @param userIds 用户ID列表
     */
    void validateUsers(List<Long> userIds);
}
