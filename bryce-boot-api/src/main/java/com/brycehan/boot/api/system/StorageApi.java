package com.brycehan.boot.api.system;

import cn.hutool.core.io.file.FileNameUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.LocalTime;

/**
 * 存储服务 API
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface StorageApi {

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return http资源地址
     */
    default String upload(byte[] data, String path) {
        return this.upload(new ByteArrayInputStream(data), path);
    }

    /**
     * 文件上传
     *
     * @param data 文件字节流
     * @param path 文件路径，包含文件名
     * @return http资源地址
     */
    String upload(InputStream data, String path);

    /**
     * 生成路径，不包含文件名
     * @return 生成的路径
     */
    String getPath();

    /**
     * 根据文件名，生成路径
     *
     * @param fileName 文件名
     * @return 生成文件路径
     */
    default String getPath(String fileName) {
        return getPath().concat(File.separator).concat(getNewFileName(fileName));
    }

    default String getNewFileName(String fileName) {
        // 主文件名，不包含扩展名
        String prefix = FileNameUtil.getPrefix(fileName);
        // 文件扩展名
        String suffix = FileNameUtil.getSuffix(fileName);
        // 把当前时间，转换成毫秒
        long time = LocalTime.now().toNanoOfDay();
        return prefix
                .concat("_")
                .concat(String.valueOf(time))
                .concat(".")
                .concat(suffix);
    }

}
