package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.system.entity.SysLoginInfo;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.system.dto.SysLoginInfoDto;
import com.brycehan.boot.system.dto.SysLoginInfoPageDto;
import com.brycehan.boot.system.vo.SysLoginInfoVo;
import org.springframework.scheduling.annotation.Async;

/**
 * 系统登录信息服务类
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
public interface SysLoginInfoService extends IService<SysLoginInfo> {

    /**
     * 添加系统登录信息
     *
     * @param sysLoginInfoDto 系统登录信息Dto
     */
    void save(SysLoginInfoDto sysLoginInfoDto);

    /**
     * 更新系统登录信息
     *
     * @param sysLoginInfoDto 系统登录信息Dto
     */
    void update(SysLoginInfoDto sysLoginInfoDto);

    /**
     * 系统登录信息分页查询信息
     *
     * @param sysLoginInfoPageDto 系统登录信息分页搜索条件
     * @return 分页信息
     */
    PageResult<SysLoginInfoVo> page(SysLoginInfoPageDto sysLoginInfoPageDto);

    /**
     * 异步记录登录信息
     *
     * @param username 用户账号
     * @param status 状态
     * @param message 消息
     * @param args 参数
     */
    @Async
    void AsyncRecordLoginInfo(String userAgent,
                              String ip,
                              final String username,
                              final Integer status,
                              final String message,
                              final Object... args);
}
