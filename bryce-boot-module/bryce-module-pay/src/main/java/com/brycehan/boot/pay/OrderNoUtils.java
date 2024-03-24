package com.brycehan.boot.pay;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号工具类
 *
 * @author Bryce Han
 * @since 2024/3/6
 */
public class OrderNoUtils {

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 获取订单编号
     *
     * @return 订单编号
     */
    public static String getOrderNo(){
        return "ORD_" + getNo();
    }

    /**
     * 获取退款单编号
     *
     * @return 退款单编号
     */
    public static String getRefundNo(){
        return "REF_" + getNo();
    }

    /**
     * 获取编号
     *
     * @return 编号
     */
    private static String getNo() {
        String dateStr = simpleDateFormat.format(new Date());
        String nonce = RandomStringUtils.randomNumeric(6);
        return dateStr.concat(nonce);
    }


}
