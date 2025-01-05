package com.brycehan.boot.common.util;

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
    public static final String[] IMAGE_EXTENSION = {"jpg", "jpeg", "png", "bmp", "gif"};
    // Excel文件后缀
    public static final String[] EXCEL_EXTENSION = {"xlsx", "xls", "csv"};
    // Flash文件后缀
    public static final String[] FLASH_EXTENSION = {"swf", "flv"};
    // 媒体文件后缀
    public static final String[] MEDIA_EXTENSION = {"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "mp4", "avi", "rmvb"};
    // 视频文件后缀
    public static final String[] VIDEO_EXTENSION = {"mp4", "avi", "rmvb"};

    // 允许上传的文件后缀
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "jpg", "jpeg", "png", "bmp", "gif",
            // word、excel、powerpoint、pdf
            "docx", "doc", "xlsx", "xls", "ppt", "pptx", "pdf", "html", "htm", "xml", "txt", "csv",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb"
    };

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
