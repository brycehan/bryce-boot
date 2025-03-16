package com.brycehan.boot.bpm.common.trigger;

import com.brycehan.boot.bpm.common.type.BpmTriggerTypeEnum;

/**
 * BPM 触发器接口
 * <p>
 * 处理不同的动作
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
public interface BpmTrigger {

    /**
     * 对应触发器类型
     *
     * @return 触发器类型
     */
    BpmTriggerTypeEnum getType();

    /**
     * 触发器执行
     *
     * @param processInstanceId 流程实例编号
     * @param param 触发器参数
     */
    void execute(String processInstanceId, String param);

}
