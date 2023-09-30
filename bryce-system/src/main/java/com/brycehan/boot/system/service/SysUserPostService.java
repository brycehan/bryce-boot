package com.brycehan.boot.system.service;

import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.SysUserPost;

import java.util.List;

/**
 * 系统用户岗位关系服务
 *
 * @author Bryce Han
 * @since 2023/09/30
 */
public interface SysUserPostService extends BaseService<SysUserPost> {

    /**
     * 保存或修改
     *
     * @param userId 用户ID
     * @param postIds 岗位IDs
     */
    void saveOrUpdate(Long userId, List<Long> postIds);

    /**
     * 查询用户的岗位IDs
     *
     * @param userId 用户ID
     * @return 拥有的岗位IDs
     */
    List<Long> getPostIdsByUserId(Long userId);

    /**
     * 根据岗位IDs，删除用户岗位关系
     *
     * @param postIds 岗位IDs
     */
    void deleteByPostIds(List<Long> postIds);

    /**
     * 根据用户IDs，删除用户岗位关系
     *
     * @param userIds 用户IDs
     */
    void deleteByUserIds(List<Long> userIds);

}
