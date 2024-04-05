package com.brycehan.boot.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 安全工具
 *
 * @author Bryce Han
 * @since 2024/3/25
 */
public class SecureUtils {

    /**
     * 将参数，生成sha1 摘要<br>
     * 开发者通过检验signature对请求进行校验。若确认此次GET请求来自Bryce服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。<br>
     * 加密/校验流程如下：<br>
     * 1）将token、timestamp、nonce三个参数进行字典序排序<br>
     * 2）将三个参数字符串拼接成一个字符串进行sha1加密<br>
     * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于Bryce<br>
     *
     * 示例：<br>
     * <code>
     *     long timestamp = System.currentTimeMillis();<br>
     *     String nonce = RandomStringUtils.randomNumeric(10);<br>
     *     String echostr = RandomStringUtils.randomNumeric(19);
     * </code>
     *
     * @param params token、timestamp、nonce参数
     * @return sha1 摘要
     */
    public static String signSha1Hex(String... params) {
        List<String> list = Arrays.asList(params);
        Collections.sort(list);

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s);
        }

        return DigestUtils.sha1Hex(stringBuilder.toString());
    }

}
