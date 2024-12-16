package com.brycehan.boot.common.constant;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_TIME;

/**
 * 数据状态常量
 *
 * @since 2022/7/21
 * @author Bryce Han
 */
public class DataConstants {

    /**
     * 默认角色ID
     */
    public static final Long DEFAULT_ROLE_ID = 2L;

    /** 分页页码 */
    public static final String PAGE = "page";

    /**
     * 默认分页条数
     */
    public static final int pageSize = 10;

    /** 根ID */
    public static final Long TREE_ROOT_ID = 0L;

    /** 数据权限范围 */
    public static final String DATA_SCOPE = "dataScope";

    /**
     * 默认排序列
     */
    public static final String DEFAULT_SORT_COLUMN = "sort";

    /**
     * 默认排序升序
     */
    public static final boolean DEFAULT_SORT_IS_ASC = true;

    /**
     * 超级管理员名称
     */
    public static final String SUPER_ADMIN_NAME = "超级管理员";

    /**
     * 超级管理员角色
     */
    public static final String ROLE_SUPER_ADMIN_CODE = "ROLE_SUPER_ADMIN";

    /**
     * 超级管理员角色ID
     */
    public static final Long ROLE_SUPER_ADMIN_ID = 1L;

    /**
     * 角色前缀
     */
    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * PG驱动
     */
    public static final String PG_DRIVER = "org.postgresql.Driver";

    /**
     * 全局日期时间格式
     * <br>
     * yyyy-MM-dd HH:mm:ss<br>
     * yyyy-MM-dd HH:mm<br>
     * yyyy-MM-dd HH:mm:ss.SSSSSS<br>
     */
    public static final DateTimeFormatter DATETIME_FORMATTER = new DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE).appendLiteral(' ').append(ISO_TIME)
            .toFormatter();

    /**
     * 公司名称
     */
    public static final String COMPANY_NAME = "Bryce 团队";

    /**
     * 电子邮件验证码主题
     */
    public static final String COMPANY_EMAIL_VERIFY_CODE_SUBJECT = "电子邮件验证码：";

}
