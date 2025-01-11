package com.brycehan.boot.framework.storage.service;

import cn.hutool.core.io.IoUtil;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.storage.config.properties.QiniuStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 七牛云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class QiniuStorageService extends StorageService {

    private final UploadManager uploadManager;
    private final Auth auth;
    private final String token;

    public QiniuStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        QiniuStorageProperties qiniu = storageProperties.getQiniu();

        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
        auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
        token = auth.uploadToken(qiniu.getBucketName());
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        try {
            Response response = uploadManager.put(IOUtils.toByteArray(data), path, token);
            if(!response.isOK()) {
                throw new RuntimeException(response.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        }

        return storageProperties.getConfig().getEndpoint().concat("/").concat(path);
    }

    @Override
    public void download(String path, String filename) {
        QiniuStorageProperties qiniu = storageProperties.getQiniu();
        HttpServletResponse response = ServletUtils.getResponse();

        // 下载链接
        URL url = null;
        try {// 获取下载链接
            String encodedPath = URLEncoder.encode(path, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            DownloadUrl downloadUrl = new DownloadUrl(qiniu.getDomain(), true, encodedPath);
            // 带有效期1小时
            long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
            long deadline = System.currentTimeMillis()/1000 + expireInSeconds;
            // 生成下载链接，并创建URL对象
            url = new URL(downloadUrl.buildURL(auth, deadline));
        }catch (Exception e) {
            log.error("QINIU Obs 连接出错：{}", e.getMessage());
        }

        Assert.notNull(url, "下载文件不存在");
        // 将文件输出到Response
        try(InputStream inputStream = url.openStream();
            OutputStream outputStream = response.getOutputStream()) {
            String filenameEncoded = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + filenameEncoded);
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            long length = IoUtil.copy(inputStream, outputStream, 1024 * 1024);
            response.setContentLength((int) length);
        } catch (Exception e) {
            log.error("下载文件出错：{}", e.getMessage());
        }
    }
}
