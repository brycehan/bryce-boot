package com.brycehan.boot.monitor.controller;

import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.monitor.entity.dto.UserOnlinePageDto;
import com.brycehan.boot.monitor.entity.vo.UserOnlineVo;
import com.brycehan.boot.monitor.service.UserOnlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 在线用户API
 *
 * @since 2023/10/12
 * @author Bryce Han
 */
@Tag(name = "在线用户")
@RestController
@RequestMapping(path = "/system/onlineUser")
@RequiredArgsConstructor
public class UserOnlineController {

    private final UserOnlineService userOnlineService;

    /**
     * 在线用户分页查询
     *
     * @param userOnlinePageDto 查询条件
     * @return 在线用户分页列表
     */
    @Operation(summary = "在线用户分页查询")
    @PreAuthorize("hasAuthority('monitor:onlineUser:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<UserOnlineVo>> page(@Validated @RequestBody UserOnlinePageDto userOnlinePageDto) {
        if (StringUtils.isNotBlank(userOnlinePageDto.getUsername()) || StringUtils.isNotBlank(userOnlinePageDto.getLoginIp())) {
            PageResult<UserOnlineVo> pageResult = userOnlineService.pageByUsernameAndLoginIp(userOnlinePageDto);
            return ResponseResult.ok(pageResult);
        } else {
            PageResult<UserOnlineVo> pageResult = userOnlineService.page(userOnlinePageDto);
            return ResponseResult.ok(pageResult);
        }


    }

    /**
     * 强制退出
     *
     * @param userKey 会话存储Key
     * @return 响应结果
     */
    @Operation(summary = "强制退出")
    @OperateLog(type = OperatedType.FORCE_QUIT)
    @PreAuthorize("hasAuthority('monitor:onlineUser:delete')")
    @DeleteMapping(path = "/{userKey}")
    public ResponseResult<Void> delete(@PathVariable String userKey) {
        userOnlineService.deleteLoginUser(userKey);
        return ResponseResult.ok();
    }

}
