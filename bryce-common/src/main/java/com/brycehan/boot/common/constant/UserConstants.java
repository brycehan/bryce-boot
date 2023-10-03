package com.brycehan.boot.common.constant;

/**
 * 用户常量
 *
 * @since 2022/9/22
 * @author Bryce Han
 */
public class UserConstants {

    /**
     * null 用户ID
     */
    public static final Long NULL_USER_ID = -1L;

    /**
     * 平台内系统用户的唯一标志
     */
    public static final String SYS_USER = "sys_user";

    /**
     * 正常状态
     */
    public static final String NORMAL = "1";

    /**
     * 异常状态
     */
    public static final String EXCEPTION = "0";

    /**
     * 用户封禁状态
     */
    public static final String USER_DISABLE = "0";

    /**
     * 角色封禁状态
     */
    public static final String ROLE_DISABLE = "0";

    /**
     * 部门正常状态
     */
    public static final String DEPT_NORMAL = "1";

    /**
     * 部门停用状态
     */
    public static final String DEPT_DISABLE = "0";

    /**
     * 字典正常状态
     */
    public static final String DICT_NORMAL = "1";

    /**
     * 是否为系统默认（是）
     */
    public static final String YES = "Y";

    /**
     * 是否菜单外链（是）
     */
    public static final boolean YES_FRAME = true;

    /**
     * 是否菜单外链（否）
     */
    public static final boolean NO_FRAME = false;

    /**
     * 菜单类型（目录）
     */
    public static final String TYPE_DIR = "D";

    /**
     * 菜单类型（菜单）
     */
    public static final String TYPE_MENU = "M";

    /**
     * 菜单类型（按钮）
     */
    public static final String TYPE_BUTTON = "B";

    public static final String ADMIN_USER_ID = "1";

    /**
     * Layout组件标识
     */
    public final static String LAYOUT = "Layout";

    /**
     * ParentView组件标识
     */
    public final static String PARENT_VIEW = "ParentView";

    /**
     * InnerLink组件标识
     */
    public final static String INNER_LINK = "InnerLink";

    /**
     * 校验返回结果码
     */
    public final static String UNIQUE = "1";
    public final static String NOT_UNIQUE = "0";

    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 30;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 20;
}
