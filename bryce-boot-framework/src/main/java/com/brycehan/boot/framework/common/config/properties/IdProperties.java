package com.brycehan.boot.framework.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ID属性
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.id")
public class IdProperties {

    /**
     * 机器码
     */
    private short workerId;

    /**
     * 机器码位长
     */
    private byte workerIdBitLength;

    /**
     * 用来限制每毫秒生成的ID个数
     */
    private long seqBitLength;

    /**
     * 基础时间
     */
    private long baseTime;

}
