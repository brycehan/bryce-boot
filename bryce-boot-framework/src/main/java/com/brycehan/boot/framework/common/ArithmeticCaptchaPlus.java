package com.brycehan.boot.framework.common;

import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;

/**
 * @author Bryce Han
 * @since 2025/2/12
 */
@Slf4j
public class ArithmeticCaptchaPlus extends ArithmeticCaptcha {
    private String arithmeticString;  // 计算公式

    public ArithmeticCaptchaPlus() {
    }

    public ArithmeticCaptchaPlus(int width, int height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    public ArithmeticCaptchaPlus(int width, int height, int len) {
        this(width, height);
        setLen(len);
    }

    @SuppressWarnings("unused")
    public ArithmeticCaptchaPlus(int width, int height, int len, Font font) {
        this(width, height, len);
        setFont(font);
    }

    @Override
    protected char[] alphas() {
        StringBuilder sb;
        do {
            sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append(num(10));
                if (i < len - 1) {
                    int type = num(1, 4);
                    if (type == 1) {
                        sb.append("+");
                    } else if (type == 2) {
                        sb.append("-");
                    } else if (type == 3) {
                        sb.append("x");
                    }
                }
            }
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("nashorn");
            try {
                chars = String.valueOf(engine.eval(sb.toString().replaceAll("x", "*")));
            } catch (Exception e) {
                log.error("算术验证码答案计算异常", e);
                throw new RuntimeException("算术验证码答案计算异常");
            }
        } while (Integer.parseInt(chars) < 0); // 验证码计算结果不能为负数

        sb.append("=?");
        arithmeticString = sb.toString();
        return chars.toCharArray();
    }

    public String getArithmeticString() {
        checkAlpha();
        return arithmeticString;
    }
}
