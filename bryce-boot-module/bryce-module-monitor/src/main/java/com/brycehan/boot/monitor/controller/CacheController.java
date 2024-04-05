package com.brycehan.boot.monitor.controller;

import cn.hutool.core.map.MapUtil;
import com.brycehan.boot.common.base.http.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 缓存监控API
 *
 * @since 2023/7/12
 * @author Bryce Han
 */
@Tag(name = "缓存监控")
@RestController
@RequestMapping(path = "/monitor/cache")
@RequiredArgsConstructor
public class CacheController {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存相关信息
     *
     * @return 响应结果
     */
    @Operation(summary = "缓存相关信息")
    @PreAuthorize("hasAuthority('monitor:cache:info')")
    @GetMapping(path = "/info")
    public ResponseResult<Map<String, Object>> info() {
        Map<String, Object> result = new HashMap<>();

        // 1、获取Redis详情
        Properties info = (Properties) this.redisTemplate.execute((RedisCallback<?>) RedisServerCommands::info);
        result.put("info", info);

        // 2、获取Key的数量
        Long dbSize = (Long) this.redisTemplate.execute((RedisCallback<?>) RedisServerCommands::dbSize);
        result.put("keyCount", dbSize);

        // 3、获取命令调用量
        List<Map<String, Object>> list = new ArrayList<>();
        Properties commandStats = (Properties) this.redisTemplate.execute((RedisCallback<?>)
                connection -> connection.serverCommands().info("commandStats"));
        if(MapUtil.isNotEmpty(commandStats)) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, Object> item = new HashMap<>();
                String property = commandStats.getProperty(key);
                item.put("name", StringUtils.substring(key, 8));
                item.put("value", StringUtils.substringBetween(property, "calls=", ",use"));
                list.add(item);
            });
        }
        result.put("commandStats", list);

        return ResponseResult.ok(result);
    }

}
