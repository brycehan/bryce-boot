package com.brycehan.boot.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ID属性
 *
 * @author Bryce Han
 * @since 2022/5/13
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "bryce.id")
public class IdProperties {

    /**
     * 基础时间
     */
    private long baseTime;

    /**
     * 机器码
     */
    private short workerId;

    /**
     * 机器码位长
     */
    private byte workerIdBitLength;

}
