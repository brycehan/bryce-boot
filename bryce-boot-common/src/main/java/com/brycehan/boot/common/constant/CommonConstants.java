package com.brycehan.boot.common.constant;

import java.nio.charset.StandardCharsets;

/**
 * 常用常量
 *
 * @since 2022/5/18
 * @author Bryce Han
 */
public class CommonConstants {

    /**
     * 界定符匹配正则表达式
     */
    public static final String DELI_STR_PATTERN = "\\{\\}";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = StandardCharsets.UTF_8.name();

    /**
     * www主域
     */
    public static final String WWW = "www.";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 登录失败
     */
    public static final boolean LOGIN_FAIL = false;

    public static final String WECHAT_MP_MENU = "wechat.mp.menu";

    /**
     * 资源映射路径前缀
     */
    public static final String RESOURCE_PREFIX = "/attachment";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = {"com.brycehan"};

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.brycehan.common.utils.file"};

}
