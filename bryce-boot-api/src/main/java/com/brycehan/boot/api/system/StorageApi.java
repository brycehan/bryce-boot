package com.brycehan.boot.api.system;

import com.brycehan.boot.api.system.vo.StorageVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 存储服务 Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface StorageApi {

    /**
     * 文件上传
     *
     * @param file MultipartFile 文件
     * @return http资源地址
     */
    StorageVo upload(MultipartFile file);
}
