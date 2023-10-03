package com.brycehan.boot.common.util;

import com.brycehan.boot.common.base.context.SpringContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 获取i18n资源信息
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
public class MessageUtils {

    public static final MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);

    /**
     * 根据消息编码和参数，获取消息，委托给Spring MessageSource
     *
     * @param code 消息编码
     * @param args 参数
     * @return 获取国际化值
     */
    public static String getMessage(String code, Object... args) {

        assert MessageUtils.messageSource != null;
        return MessageUtils.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
