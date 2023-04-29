package com.brycehan.boot.common.util;

import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.exception.file.FileNameLengthLimitExceededException;
import com.brycehan.boot.common.exception.file.FileSizeLimitExceededException;
import com.brycehan.boot.common.exception.file.InvalidExtensionException;
//import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * 文件上传工具类
 *
 * @author Bryce Han
 * @since 2022/7/15
 */
@Component
public class FileUploadUtils {

    /**
     * 默认文件最大大小，50M
     */
    public static final long DEFAULT_FILE_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认文件名最大长度，100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传地址
     */
    public static String DEFAULT_BASE_DIR;

    /**
     * 文件上传
     *
     * @param baseDir           所属模块的基目录
     * @param file              上传的文件
     * @param allowedExtensions 上传文件允许的类型
     * @return 上传成功的文件访问路径
     * @throws IOException 读写文件出错异常
     */
//    public static String upload(String baseDir, MultipartFile file, String[] allowedExtensions) throws IOException {
//        // 1、校验文件
//        assertAllowed(file, allowedExtensions);
//        // 2、生成存储文件名并存储
//        String filename = generateFilename(file);
//        String path = DEFAULT_BASE_DIR
//                .concat(baseDir);
//        String absolutePath = getAbsoluteFile(path, filename).getAbsolutePath();
//        file.transferTo(Paths.get(absolutePath));
//        // 3、获取资源访问映射文件名
//        return getResourceAccessFilename(baseDir, filename);
//    }

    /**
     * 校验文件的名称、大小和扩展名是否允许
     *
     * @param file             文件
     * @param allowedExtension 允许的扩展名
     */
//    public static void assertAllowed(MultipartFile file, String[] allowedExtension) {
//        // 1、文件名长度校验
//        int fileNameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
//        if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
//            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
//        }
//        // 2、文件大小校验
//        if (file.getSize() > DEFAULT_FILE_MAX_SIZE) {
//            throw new FileSizeLimitExceededException(DEFAULT_FILE_MAX_SIZE / 1024 / 1024);
//        }
//        // 3、文件扩展名校验
//        String originalFilename = file.getOriginalFilename();
//        String extensionName = FileUploadUtils.getExtension(file);
//
//        if (Arrays.stream(allowedExtension)
//                .noneMatch(item -> item.equalsIgnoreCase(extensionName))) {
//            throw new InvalidExtensionException(originalFilename, extensionName, allowedExtension);
//        }
//    }

    /**
     * 生成文件名
     *
     * @param file 文件
     * @return 文件名
     */
//    public static String generateFilename(MultipartFile file) {
//
//        return StringFormatUtils.format("{}/{}_{}.{}", LocalDate.now().toString(),
//                FilenameUtils.getBaseName(file.getOriginalFilename()),
//                SequenceUtils.getId(SequenceUtils.UPLOAD_SEQUENCE_TYPE),
//                FileUploadUtils.getExtension(file)
//        );
//    }

    /**
     * 获取文件要存储的绝对路径
     *
     * @param uploadDir 上传的目录
     * @param filename  文件名称
     * @return 文件要存储的绝对路径
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File getAbsoluteFile(String uploadDir, String filename) {
        File file = new File(uploadDir.concat(File.separator).concat(filename));
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    /**
     * 获取资源访问映射文件名
     *
     * @param baseDir  基础目录
     * @param filename 文件名称
     * @return 资源访问映射文件名
     */
    private static String getResourceAccessFilename(String baseDir, String filename) {
        return CommonConstants.RESOURCE_PREFIX
                .concat(baseDir)
                .concat(File.separator)
                .concat(filename);
    }

//    /**
//     * 获取文件的扩展名
//     *
//     * @param file 文件
//     * @return 扩展名
//     */
//    public static String getExtension(MultipartFile file) {
//        // 1、根据文件名后缀获取
//        String extensionName = FilenameUtils.getExtension(file.getOriginalFilename());
//        // 2、根据ContentType获取
//        if (StringUtils.isEmpty(extensionName)) {
//            extensionName = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
//        }
//        return extensionName;
//    }

}
