package com.brycehan.boot.monitor.controller;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.monitor.vo.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控API
 *
 * @since 2023/7/12
 * @author Bryce Han
 */
@Tag(name = "服务器监控", description = "server")
@RequestMapping(path = "/monitor/server")
@RestController
public class ServerController {

    /**
     * 服务器相关信息
     *
     * @return 响应结果
     */
    @PreAuthorize("hasAuthority('monitor:server:info')")
    @GetMapping(path = "/info")
    public ResponseResult<Server> server() {
        Server server = new Server();
        System.out.println(JsonUtils.writeValueAsString(server));
        return ResponseResult.ok(server);
    }

}
