package com.brycehan.boot.framework.storage.service;

import cn.hutool.core.io.IoUtil;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import com.brycehan.boot.framework.storage.config.properties.TencentStorageProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
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
 * 腾讯云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class TencentStorageService extends StorageService {

    private final COSCredentials credentials;

    private final ClientConfig clientConfig;

    public TencentStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        TencentStorageProperties tencent = storageProperties.getTencent();
        credentials = new BasicCOSCredentials(tencent.getAccessKey(), tencent.getSecretKey());

        clientConfig = new ClientConfig(new Region(tencent.getRegion()));
        clientConfig.setHttpProtocol(HttpProtocol.https);
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        TencentStorageProperties tencent = this.storageProperties.getTencent();
        COSClient client = new COSClient(credentials, clientConfig);
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(data.available());

            PutObjectRequest request = new PutObjectRequest(tencent.getBucketName(),
                    path, data, metadata);
            PutObjectResult result = client.putObject(request);

            if(result.getETag() == null) {
                throw new RuntimeException("上传文件失败，请检查配置信息");
            }
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        } finally {
            client.shutdown();
        }

        return this.storageProperties.getConfig().getEndpoint().concat("/").concat(path);
    }

    @Override
    public void download(String path, String filename) {
        TencentStorageProperties tencent = storageProperties.getTencent();
        HttpServletResponse response = ServletUtils.getResponse();
        COSClient client = new COSClient(credentials, clientConfig);

        COSObject object = null;
        try {// 创建GetObjectRequest对象
            GetObjectRequest getObjectRequest = new GetObjectRequest(tencent.getBucketName(), path);
            // 获取文件对象
            object = client.getObject(getObjectRequest);
        }catch (Exception e) {
            log.error("Tencent COS 连接出错：{}", e.getMessage());
        }

        Assert.notNull(object, "下载文件不存在");
        // 将文件输出到Response
        try(InputStream inputStream = object.getObjectContent();
            OutputStream outputStream = response.getOutputStream()) {
            String filenameEncoded = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + filenameEncoded);
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            response.setContentLength((int) object.getObjectMetadata().getContentLength());
            IoUtil.copy(inputStream, outputStream, 1024 * 1024);
        } catch (Exception e) {
            log.error("下载文件出错：{}", e.getMessage());
        }
    }
}
