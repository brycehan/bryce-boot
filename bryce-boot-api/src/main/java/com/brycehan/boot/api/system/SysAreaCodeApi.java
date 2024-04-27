package com.brycehan.boot.api.system;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * 地区编码Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface SysAreaCodeApi {

    /**
     * 根据地区编码获取扩展名称
     *
     * @param areaCode 地区编码
     * @return 扩展名称
     */
    String getExtNameByCode(@RequestParam String areaCode);

    /**
     * 获取地区位置
     *
     * @param areaCode 地区编码
     * @return 地区位置
     */
    String getFullLocation(@RequestParam String areaCode);

}
