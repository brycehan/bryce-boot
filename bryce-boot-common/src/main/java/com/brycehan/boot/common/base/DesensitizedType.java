package com.brycehan.boot.common.base;

/**
 * 脱敏类型
 *
 * @author Bryce Han
 * @since 2024/6/23
 */
@SuppressWarnings("unused")
public enum DesensitizedType {
    /**
     * 用户ID
     */
    USER_ID,
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 手机号
     */
    PHONE,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 中国大陆车牌，包含普通车辆、新能源车辆
     */
    CAR_LICENSE,
    /**
     * 银行卡
     */
    BANK_CARD,
    /**
     * IPv4地址
     */
    IPV4,
    /**
     * IPv6地址
     */
    IPV6,
    /**
     * 定义了一个first_mask的规则，只显示第一个字符。
     */
    FIRST_MASK,
    /**
     * 清空为null
     */
    CLEAR_TO_NULL,
    /**
     * 清空为""
     */
    CLEAR_TO_EMPTY

}
