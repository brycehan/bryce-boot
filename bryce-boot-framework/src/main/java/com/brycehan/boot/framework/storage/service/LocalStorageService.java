package com.brycehan.boot.framework.storage.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.storage.config.properties.LocalStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * 本地存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
public class LocalStorageService extends StorageService {

    public LocalStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {

        LocalStorageProperties local = storageProperties.getLocal();
        try {
            File file = new File(local.getAccessPath(path));

            // 没有目录，则自动创建目录
            File parent = file.getParentFile();
            if(parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("目录".concat(parent.toString()).concat("创建失败"));
            }

            IoUtil.copy(data, Files.newOutputStream(file.toPath()), 1024 * 1024);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        }

        // 安全访问的相对路径
        if (accessType == AccessType.SECURE) {
            return path;
        }
        // 公共访问路径
        return this.storageProperties.getConfig().getDomain()
                .concat(File.separator)
                .concat(local.getPrefix())
                .concat(File.separator)
                .concat(path);
    }

    @Override
    public void download(String path, String filename) {
        HttpServletResponse response = ServletUtils.getResponse();
        LocalStorageProperties local = storageProperties.getLocal();
        File file = new File(local.getAccessPath(path));

        // 获取文件名
        if (StrUtil.isBlank(filename)) {
            filename = StrUtil.subAfter(path, "/", true).split("_")[0];
            if (StrUtil.isBlank(filename)) {
                filename = "download";
            }
        }

        if (file.exists()) {
            try (InputStream inputStream = Files.newInputStream(file.toPath());
                 OutputStream outputStream = response.getOutputStream()) {
                String filenameEncoded = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + filenameEncoded);
                response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
                response.setContentLength((int) file.length());
                IoUtil.copy(inputStream, outputStream, 1024 * 1024);
            } catch (IOException e) {
                throw new RuntimeException("下载文件失败：", e);
            }
        } else {
            throw new RuntimeException("文件不存在");
        }
    }
}
