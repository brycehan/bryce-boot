package com.brycehan.boot.common.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 用户属性
 *
 * @author Bryce Han
 * @since 2022/9/19
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bryce.user")
public class UserProperties {

    /**
     * 密码
     */
    private Password password;

    /**
     * 密码
     *
     * @author Bryce Han
     * @since 2022/9/29
     */
    @Getter
    @Setter
    public static class Password {

        /**
         * 密码最大错误次数
         */
        private int maxRetryCount = 5;

        /**
         * 密码锁定间隔（默认10分钟）
         */
        private int lockDurationMinutes = 10;
    }

}
