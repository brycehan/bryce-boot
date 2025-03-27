package com.brycehan.boot.api.system;

import com.brycehan.boot.api.system.vo.BpmUserVo;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统用户Api
 *
 * @since 2024/4/7
 * @author Bryce Han
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
     * 根据部门ID列表获取用户信息
     *
     * @param deptIds 部门ID列表
     * @return 用户信息列表
     */
    List<BpmUserVo> getUsersByDeptIds(List<Long> deptIds);

    /**
     * 根据岗位ID列表获取用户信息
     *
     * @param postIds 岗位ID列表
     * @return 用户信息列表
     */
    List<BpmUserVo> getUsersByPostIds(List<Long> postIds);

    /**
     * 获得用户 Map
     *
     * @param userIds 用户编号数组
     * @return 用户 Map
     */
    default Map<Long, BpmUserVo> getUserMap(List<Long> userIds) {
        List<BpmUserVo> users = getUsers(userIds);
        return users.stream().collect(Collectors.toMap(BpmUserVo::getId, Function.identity()));
    }

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
