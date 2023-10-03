package com.brycehan.boot.framework.operationlog;

import com.brycehan.boot.common.base.RedisKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 操作日志服务
 *
 * @since 2023/8/28
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class OperateLogService {

    private final RedisTemplate<String, OperateLogDto> redisTemplate;

    @Async
    public void save(OperateLogDto operateLogDto){
        String operateLogKey = RedisKeys.getOperateLogKey();
        // 保存到Redis队列
        this.redisTemplate.opsForList()
                .leftPush(operateLogKey, operateLogDto);
    }
}
