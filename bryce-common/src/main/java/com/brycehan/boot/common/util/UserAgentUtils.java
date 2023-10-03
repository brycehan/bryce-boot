package com.brycehan.boot.common.util;

import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * UserAgent工具类
 *
 * @link <a href="https://github.com/blueconic/browscap-java">browscap-java</a>
 * @since 2022/9/20
 * @author Bryce Han
 */
@Slf4j
@Component
public class UserAgentUtils {

    /**
     * 使用默认字段创建解析器
     */
    public static UserAgentParser parser = null;

    static {
        log.info("初始化UserAgent解析器...");
        try {
            parser = new UserAgentService().loadParser();
        } catch (IOException | ParseException e) {
            log.error("UserAgentParser初始化失败：{}", e.getMessage());
        }
        log.info("初始化UserAgent解析器完成");
    }
}
