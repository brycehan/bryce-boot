package com.brycehan.boot.framework.common.xss;

import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

/**
 * Xss 过滤工具
 *
 * @since 2023/10/26
 * @author Bryce Han
 */
public class XssUtils {

    /**
     * 不格式化
     */
    public static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    /**
     * Xss 过滤
     *
     * @param content 需要过滤的内容
     * @return 过滤后的内容
     * see org.apache.commons.text.StringEscapeUtils#escapeHtml4(String) StringEscapeUtils.escapeHtml4(content) 或者用这个方法转换，而不用过滤
     */
    public static String filter(String content) {
        if(StrUtil.isBlank(content)) {
            return content;
        }

        return Jsoup.clean(content, "", Safelist.relaxed(), outputSettings);
    }

}
