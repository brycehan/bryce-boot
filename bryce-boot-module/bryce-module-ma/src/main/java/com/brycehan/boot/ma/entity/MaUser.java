package com.brycehan.boot.ma.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 微信小程序用户entity
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Data
@TableName("brc_ma_user")
public class MaUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别（0女，1男）
     */
    private String gender;

    /**
     * 所在国家
     */
    private String country;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 区/县编码
     */
    private String county;

    /**
     * 用户语言
     */
    private String language;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * openid
     */
    private String openId;

    /**
     * union_id
     */
    private String unionId;

    /**
     * 账号
     */
    private String account;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 职业
     */
    private String profession;

    /**
     * 标签ID列表
     */
    private Object tagIds;

    /**
     * 用户组
     */
    private String groupId;

    /**
     * 二维码扫码场景
     */
    private String qrSceneStr;

    /**
     * 地理位置纬度
     */
    private Double geoLatitude;

    /**
     * 地理位置经度
     */
    private Double geoLongitude;

    /**
     * 地理位置精度
     */
    private Double geoPrecision;

    /**
     * 会话密钥
     */
    private String sessionKey;

    /**
     * 会话创建时间
     */
    private LocalDateTime sessionCreatedTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

}
