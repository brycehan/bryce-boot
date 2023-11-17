package com.brycehan.boot.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * BigDecimal 工具类
 *
 * @since 2023/10/11
 * @author Bryce Han
 */
public class BigDecimalUtils {

    public static String format(BigDecimal value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(value);
    }

}
