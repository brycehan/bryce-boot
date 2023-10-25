package com.brycehan.boot.framework.xss;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HTMLFilter;

/**
 * Xss 过滤工具
 *
 * @since 2023/10/26
 * @author Bryce Han
 */
public class XssUtils {
    private static final ThreadLocal<HTMLFilter> HTML_FILTER = ThreadLocal.withInitial(() -> {
        HTMLFilter htmlFilter = new HTMLFilter();
        // 避免 " 被转换成 &quot; 字符
        ReflectUtil.setFieldValue(htmlFilter, "encodeQuotes", false);
        return htmlFilter;
    });

    /**
     * Xss 过滤
     *
     * @param content 需要过滤的内容
     * @return 过滤后的内容
     * @see  org.apache.commons.text.StringEscapeUtils#escapeHtml4(String) StringEscapeUtils.escapeHtml4(content) 或者用这个方法转换，而不用过滤
     */
    public static String filter(String content) {
        if(StrUtil.isBlank(content)) {
            return content;
        }

        return HTML_FILTER.get().filter(content);
    }
}
