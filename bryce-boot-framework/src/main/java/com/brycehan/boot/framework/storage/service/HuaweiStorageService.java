package com.brycehan.boot.framework.storage.service;

import cn.hutool.core.io.IoUtil;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.storage.config.properties.HuaweiStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
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
 * 华为云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class HuaweiStorageService extends StorageService {

    private final ObsClient obsClient;

    public HuaweiStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        HuaweiStorageProperties huawei = this.storageProperties.getHuawei();
        // 初始化obsClient实例
        obsClient = new ObsClient(huawei.getAccessKey(),
                huawei.getSecretKey(),
                huawei.getEndPoint());
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        HuaweiStorageProperties huawei = storageProperties.getHuawei();

        try (obsClient) {
            obsClient.putObject(huawei.getBucketName(), path, data);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        }

        return storageProperties.getConfig().getEndpoint().concat("/").concat(path);
    }

    @Override
    public void download(String path, String filename) {
        HuaweiStorageProperties huawei = storageProperties.getHuawei();
        HttpServletResponse response = ServletUtils.getResponse();

        ObsObject object = null;
        try {// 获取对象
            object = obsClient.getObject(huawei.getBucketName(), path);
        } catch (Exception e) {
            log.error("Huawei Obs 连接出错：{}", e.getMessage());
        }

        Assert.notNull(object, "下载文件不存在");
        // 将文件输出到Response
        try(InputStream inputStream = object.getObjectContent();
            OutputStream outputStream = response.getOutputStream()) {
            String filenameEncoded = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + filenameEncoded);
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            response.setContentLength(object.getMetadata().getContentLength().intValue());
            IoUtil.copy(inputStream, outputStream, 1024 * 1024);
        } catch (Exception e) {
            log.error("下载文件出错：{}", e.getMessage());
        }
    }
}
