package com.brycehan.boot.pay.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 商品entity
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_pay_product")
public class PayProduct extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 价格（分）
     */
    private Integer price;

}
