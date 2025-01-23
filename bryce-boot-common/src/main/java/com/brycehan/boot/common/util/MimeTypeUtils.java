package com.brycehan.boot.common.util;

import java.util.List;

/**
 * MIME类型工具类
 *
 * @author Bryce Han
 * @since 2025/1/4
 */
@SuppressWarnings("unused")
public class MimeTypeUtils {
    // 图片内容类型
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_JPG = "image/jpg";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_BMP = "image/bmp";
    public static final String IMAGE_GIF = "image/gif";
    // 图片文件后缀
    public static final List<String> IMAGE_EXTENSION = List.of("jpg", "jpeg", "png", "bmp", "gif");
    // Excel文件后缀
    public static final List<String> EXCEL_EXTENSION = List.of("xlsx", "xls", "csv");
    // Flash文件后缀
    public static final List<String> FLASH_EXTENSION = List.of("swf", "flv");
    // 媒体文件后缀
    public static final List<String> MEDIA_EXTENSION = List.of("swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "mp4", "avi", "rmvb");
    // 视频文件后缀
    public static final List<String> VIDEO_EXTENSION =  List.of("mp4", "avi", "rmvb");

    // 允许上传的文件后缀
    public static final List<String> DEFAULT_ALLOWED_EXTENSION = List.of(
            // 图片
            "jpg", "jpeg", "png", "bmp", "gif",
            // word、excel、powerpoint、pdf
            "docx", "doc", "xlsx", "xls", "ppt", "pptx", "pdf", "html", "htm", "xml", "txt", "csv",
            // 压缩文件
            "rar", "zip", "tar.gz", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb"
    );

    /**
     * 根据文件内容类型获取文件后缀
     *
     * @param contentType 文件类型
     * @return 文件后缀
     */
    public static String getExtension(String contentType) {
        return switch (contentType) {
            case IMAGE_PNG -> "png";
            case IMAGE_JPG -> "jpg";
            case IMAGE_JPEG -> "jpeg";
            case IMAGE_BMP -> "bmp";
            case IMAGE_GIF -> "gif";
            default -> "";
        };
    }
}
