package com.brycehan.boot.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 序列生成工具类
 *
 * @since 2022/11/3
 * @author Bryce Han
 */
public class SequenceUtils {

    /**
     * 通用序列类型
     */
    public static final String COMMON_SEQUENCE_TYPE = "common";

    /**
     * 上传序列类型
     */
    public static final String UPLOAD_SEQUENCE_TYPE = "upload";

    /**
     * 通用接口序列数
     */
    private static final AtomicInteger commonSequence = new AtomicInteger(1);

    /**
     * 上传接口序列数
     */
    private static final AtomicInteger uploadSequence = new AtomicInteger(1);

    /**
     * 机器标识
     */
    private static final String machineCode = "A";

    /**
     * 通用接口序列号 yyyyMMddHHmmssSSS + 一位机器标识 + length长度循环递增字符串
     *
     * @param atomicInteger 序列数
     * @param length        序列长度
     * @return 序列值
     */
    public static String getId(AtomicInteger atomicInteger, int length) {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
                .concat(machineCode)
                .concat(getSequence(atomicInteger, length));
    }

    /**
     * 通用接口序列号 yyyyMMddHHmmssSSS + 一位机器标识 + 3位长度循环递增字符串
     *
     * @param type 序列数类型
     * @return 序列值
     */
    public static String getId(String type) {
        AtomicInteger atomicInt = commonSequence;
        if (UPLOAD_SEQUENCE_TYPE.equals(type)) {
            atomicInt = uploadSequence;
        }
        return getId(atomicInt, 3);
    }

    /**
     * 通用接口序列号 yyyyMMddHHmmssSSS + 一位机器标识 + 3位长度循环递增字符串
     *
     * @return 序列值
     */
    public static String getId() {
        return getId(COMMON_SEQUENCE_TYPE);
    }

    /**
     * 序列循环递增字符串[1, 10的length次幂)，用0左补齐length位数
     *
     * @param atomicInteger 序列数
     * @param length        序列长度
     * @return 序列值
     */
    private static String getSequence(AtomicInteger atomicInteger, int length) {
        int value;
        synchronized (SequenceUtils.class) {
            value = atomicInteger.getAndIncrement();
            // 如果更新后值>=10的length次幂则重置为1
            int maxSequence = (int) Math.pow(10, length);
            if (atomicInteger.get() >= maxSequence) {
                atomicInteger.set(1);
            }
        }

        // 转字符串，长度不够时用0左补齐
        return String.format("%0"
                .concat(String.valueOf(length))
                .concat("d"), value);
    }

}
