package com.brycehan.boot.quartz.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.EnumType;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 定时任务状态
 *
 * @since 2023/10/20
 * @author Bryce Han
 */
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
public enum JobStatus implements EnumType {

    PAUSE(0, "暂停"),
    NORMAL(1, "正常");

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    public static JobStatus getByDesc(String desc) {
        for (JobStatus jobStatus : JobStatus.values()) {
            if (jobStatus.getDesc().equals(desc)) {
                return jobStatus;
            }
        }
        return null;
    }

}
