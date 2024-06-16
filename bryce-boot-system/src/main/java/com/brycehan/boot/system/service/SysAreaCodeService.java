package com.brycehan.boot.system.service;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.convert.SysAreaCodeConvert;
import com.brycehan.boot.system.entity.dto.SysAreaCodeDto;
import com.brycehan.boot.system.entity.dto.SysAreaCodePageDto;
import com.brycehan.boot.system.entity.po.SysAreaCode;
import com.brycehan.boot.system.entity.vo.SysAreaCodeVo;

import java.util.List;

/**
 * 地区编码服务
 *
 * @author Bryce Han
 * @since 2024/04/12
 */
public interface SysAreaCodeService extends BaseService<SysAreaCode> {

    /**
     * 更新地区编码
     *
     * @param sysAreaCodeDto 地区编码Dto
     */
    default void update(SysAreaCodeDto sysAreaCodeDto) {
        SysAreaCode sysAreaCode = SysAreaCodeConvert.INSTANCE.convert(sysAreaCodeDto);
        this.getBaseMapper().updateById(sysAreaCode);
    }

    /**
     * 地区编码分页查询
     *
     * @param sysAreaCodePageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysAreaCodeVo> page(SysAreaCodePageDto sysAreaCodePageDto);

    /**
     * 地区编码导出数据
     *
     * @param sysAreaCodePageDto 地区编码查询条件
     */
    void export(SysAreaCodePageDto sysAreaCodePageDto);

    /**
     * 根据地区编码获取地区信息
     *
     * @param areaCode 地区编码
     * @return 地区信息
     */
    SysAreaCodeVo getByCode(String areaCode);

    /**
     * 根据地区编码获取地区名称
     *
     * @param areaCode 地区编码
     * @return 地区名称
     */
    String getNameByCode(String areaCode);

    /**
     * 根据地区编码获取扩展名称
     *
     * @param areaCode 地区编码
     * @return 扩展名称
     */
    String getExtNameByCode(String areaCode);

    /**
     * 获取地区位置
     *
     * @param areaCode 地区编码
     * @return 地区位置
     */
    String getFullLocation(String areaCode);

    /**
     * 获取地区位置列表
     *
     * @param areaCodeList 地区编码列表
     * @return 地区位置
     */
    List<String> getFullLocation(List<String> areaCodeList);

}
