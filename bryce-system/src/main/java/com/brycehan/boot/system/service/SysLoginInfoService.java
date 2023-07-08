package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.system.dto.SysLoginInfoPageDto;
import com.brycehan.boot.system.entity.SysLoginInfo;
import com.github.pagehelper.PageInfo;
import jakarta.validation.constraints.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;

/**
 * 系统登录信息服务类
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
@Validated
public interface SysLoginInfoService extends IService<SysLoginInfo> {

    /**
     * 分页查询信息结果
     *
     * @param sysLoginInfoPageDto 搜索条件
     * @return 分页信息
     */
    PageInfo<SysLoginInfo> page(@NotNull SysLoginInfoPageDto sysLoginInfoPageDto);

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
                              final String username,
                              final Integer status,
                              final String message,
                              final Object... args);
}
