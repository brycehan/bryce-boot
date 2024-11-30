package com.brycehan.boot.quartz.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.EnumType;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 定时任务组
 *
 * @since 2023/10/20
 * @author Bryce Han
 */
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
public enum JobGroup implements EnumType {

    SYSTEM("system", "系统"),
    APP("app", "应用");

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static JobGroup getByDesc(String desc) {
        for (JobGroup jobGroup : JobGroup.values()) {
            if (jobGroup.getDesc().equals(desc)) {
                return jobGroup;
            }
        }
        return null;
    }

}
