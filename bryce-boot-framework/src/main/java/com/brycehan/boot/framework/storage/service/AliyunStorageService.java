package com.brycehan.boot.framework.storage.service;

import cn.hutool.core.io.IoUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.storage.config.properties.AliyunStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 本地存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class AliyunStorageService extends StorageService {

    private final OSS ossClient;

    public AliyunStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        AliyunStorageProperties aliyun = this.storageProperties.getAliyun();
        // 初始化OSSClient实例。
        ossClient = new OSSClientBuilder().build(aliyun.getEndPoint(),
                aliyun.getAccessKeyId(),
                aliyun.getAccessKeySecret());
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        AliyunStorageProperties aliyun = storageProperties.getAliyun();

        try {
            ossClient.putObject(aliyun.getBucketName(), path, data);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        } finally {
            if(ossClient != null) {
                ossClient.shutdown();
            }
        }

        return storageProperties.getConfig().getEndpoint().concat("/").concat(path);
    }

    @Override
    public void download(String path, String filename) {
        AliyunStorageProperties aliyun = storageProperties.getAliyun();
        HttpServletResponse response = ServletUtils.getResponse();

        OSSObject object = null;
        try {// 获取对象
            object = ossClient.getObject(new GetObjectRequest(aliyun.getBucketName(), path));
        } catch (Exception e) {
            log.error("Aliyun OSS 连接出错：{}", e.getMessage());
        }

        Assert.notNull(object, "下载文件不存在");
        // 将文件输出到Response
        try(InputStream inputStream = object.getObjectContent();
                OutputStream outputStream = response.getOutputStream()) {
            String filenameEncoded = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + filenameEncoded);
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            response.setContentLength((int) object.getResponse().getContentLength());
            IoUtil.copy(inputStream, outputStream, 1024 * 1024);
        } catch (Exception e) {
            log.error("下载文件出错：{}", e.getMessage());
        }
    }
}
