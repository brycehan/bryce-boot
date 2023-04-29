package com.brycehan.boot.common.util;

import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * UserAgent工具类
 *
 * @author Bryce Han
 * @link https://github.com/blueconic/browscap-java
 * @since 2022/9/20
 */
@Slf4j
public class UserAgentUtils {

    /**
     * 使用默认字段创建解析器
     */
    public static UserAgentParser parser = null;

    static {
        try {
            parser = new UserAgentService().loadParser();
        } catch (IOException | ParseException e) {
            log.error("UserAgentParser初始化失败：{}", e.getMessage());
        }
    }
}
