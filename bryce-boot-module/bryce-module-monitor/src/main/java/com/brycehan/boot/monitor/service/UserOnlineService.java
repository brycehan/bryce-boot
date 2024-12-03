package com.brycehan.boot.monitor.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.monitor.entity.dto.UserOnlinePageDto;
import com.brycehan.boot.monitor.entity.vo.UserOnlineVo;

/**
 * 在线用户服务
 *
 * @author Bryce Han
 * @since 2024/12/2
 */
public interface UserOnlineService {

    /**
     * 带过滤条件的在线用户分页查询
     *
     * @param userOnlinePageDto 在线用户分页查询参数
     * @return 在线用户分页查询结果
     */
    PageResult<UserOnlineVo> pageByUsernameAndLoginIp(UserOnlinePageDto userOnlinePageDto);

    /**
     * 在线用户分页查询
     *
     * @param userOnlinePageDto 在线用户分页查询参数
     * @return 在线用户分页查询结果
     */
    PageResult<UserOnlineVo> page(UserOnlinePageDto userOnlinePageDto);

    /**
     * 强制退出
     *
     * @param userKey 用户Key
     */
    void deleteLoginUser(String userKey);

}
