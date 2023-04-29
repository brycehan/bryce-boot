package com.brycehan.boot.common.service;

/**
 * IP地址服务
 *
 * @author Bryce Han
 * @since 2022/9/21
 */
public interface IPAddressService {

    /**
     * 获取IP地址区域城市
     *
     * @param ip IP地址
     * @return 区域 城市，例如【山东省 济南市】
     */
    String getRealAddressByIP(String ip);
}
