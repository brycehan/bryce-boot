package com.brycehan.boot.framework.storage.service;

import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.storage.config.properties.HuaweiStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

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
        HuaweiStorageProperties huawei = storageProperties.getHuawei();
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

        if (object == null) {
            throw new RuntimeException("下载文件不存在");
        }

        // 设置响应头
        setResponseHeaders(response, filename, object.getMetadata().getContentLength().intValue());

        // 将文件输出到Response
        writeToResponse(object.getObjectContent(), response);
    }


}
