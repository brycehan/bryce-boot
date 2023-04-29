package com.brycehan.boot.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 媒体类型工具类
 *
 * @author Bryce Han
 * @since 2022/11/2
 */
public class MimeTypeUtils {

    /**
     * 图片扩展名
     */
    public static final String[] IMAGE_EXTENSION = {"jpg", "jpeg", "png", "gif", "bmp"};

    /**
     * 视频扩展名
     */
    public static final String[] VIDEO_EXTENSION = {"mp4", "avi", "rmvb"};

    /**
     * 获取文件的扩展名
     * <p>
     * 后期看 是否需要扩展jpg bmp格式
     *
     * @param mimeType mime类型
     * @return 文件的扩展名
     */
    public static String getExtension(String mimeType) {
        switch (mimeType) {
            case org.springframework.util.MimeTypeUtils.IMAGE_JPEG_VALUE:
                return "jpg";
            case org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE:
                return "png";
            case org.springframework.util.MimeTypeUtils.IMAGE_GIF_VALUE:
                return "gif";
            default:
                return StringUtils.EMPTY;
        }
    }

}
