package com.brycehan.boot.framework.config;

import com.brycehan.boot.common.properties.IdProperties;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ID配置
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(IdProperties.class)
public class IdConfig {

    private final IdProperties idProperties;

    @PostConstruct
    public void init(){
        // 创建 IdGeneratorOptions 对象，可在构造函数中输入 WorkerId：
        IdGeneratorOptions options = new IdGeneratorOptions(idProperties.getWorkerId());
        options.WorkerIdBitLength = idProperties.getWorkerIdBitLength(); // 默认值6，限定 WorkerId 最大值为2^6-1，即默认最多支持64个节点。
        options.SeqBitLength = idProperties.getWorkerIdBitLength(); // 默认值6，限制每毫秒生成的ID个数。若生成速度超过5万个/秒，建议加大 SeqBitLength 到 10。
        options.BaseTime = idProperties.getBaseTime(); // 如果要兼容老系统的雪花算法，此处应设置为老系统的BaseTime。
        // ...... 其它参数参考 IdGeneratorOptions 定义。

        // 保存参数（务必调用，否则参数设置不生效）：
        YitIdHelper.setIdGenerator(options);

        // 以上过程只需全局一次，且应在生成ID之前完成。
    }

}
