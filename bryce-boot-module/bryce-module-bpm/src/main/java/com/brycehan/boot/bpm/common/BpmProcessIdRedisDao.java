package com.brycehan.boot.bpm.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.bpm.entity.vo.BpmModelMetaInfoVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * BPM 流程 Id 编码的 Redis Dao
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Repository
public class BpmProcessIdRedisDao {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 流程 ID 的缓存
     */
    static String BPM_PROCESS_ID = "bpm:process_id:";

    /**
     * 生成序号，使用定义的 processIdRule 规则生成
     *
     * @param processIdRule 规则
     * @return 序号
     */
    public String generate(BpmModelMetaInfoVo.ProcessIdRule processIdRule) {
        // 生成日期前缀
        String infix = switch (processIdRule.getInfix()) {
            case "DAY" -> DateUtil.format(LocalDateTime.now(), "yyyyMMDD");
            case "HOUR" -> DateUtil.format(LocalDateTime.now(), "yyyyMMDDHH");
            case "MINUTE" -> DateUtil.format(LocalDateTime.now(), "yyyyMMDDHHmm");
            case "SECOND" -> DateUtil.format(LocalDateTime.now(), "yyyyMMDDHHmmss");
            default -> "";
        };

        // 生成序号
        String noPrefix = processIdRule.getPrefix() + infix + processIdRule.getPostfix();
        String key = BPM_PROCESS_ID + noPrefix;
        Long no = stringRedisTemplate.opsForValue().increment(key);
        if (StrUtil.isEmpty(infix)) {
            // 特殊：没有前缀，则不能过期，不能每次都是从 0 开始
            stringRedisTemplate.expire(key, Duration.ofDays(1L));
        }
        return noPrefix + String.format("%0" + processIdRule.getLength() + "d", no);
    }

}
