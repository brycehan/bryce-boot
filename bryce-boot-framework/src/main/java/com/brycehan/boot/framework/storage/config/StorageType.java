package com.brycehan.boot.framework.storage.config;

/**
 * 存储类型
 *
 * @since 2023/10/1
 * @author Bryce Han
 */
public enum StorageType {
    /** 本地 */
    LOCAL,
    /** Minio */
    MINIO,
    /** 阿里云 */
    ALIYUN,
    /** 腾讯云 */
    TENCENT,
    /** 七牛云 */
    QINIU,
    /** 华为云 */
    HUAWEI

}
