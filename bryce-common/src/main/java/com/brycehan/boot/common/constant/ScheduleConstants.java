package com.brycehan.boot.common.constant;

/**
 * 任务调度常量
 *
 * @since 2022/9/22
 * @author Bryce Han
 */
public class ScheduleConstants {
    public static final String TASK_CLASS_NAME = "task_class_name";

    /**
     * 执行目标key
     */
    public static final String TASK_PROPERTIES = "task_properties";

    /**
     * 默认
     */
    public static final String MISFIRE_DEFAULT = "0";

    /**
     * 立即触发执行
     */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /**
     * 触发一次执行
     */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /**
     * 不触发立即执行
     */
    public static final String MISFIRE_DO_NOTHING = "3";

    /**
     * 正常
     */
    public static final String NORMAL = "1";

    /**
     * 暂停
     */
    public static final String PAUSE = "0";

}
