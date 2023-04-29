package com.brycehan.boot.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件工具类
 *
 * @author Bryce Han
 * @since 2022/7/15
 */
public class FileUtils {

    /**
     * 获取文件名后缀
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        if (StringUtils.isEmpty(fileName) || fileName.lastIndexOf(".") == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
