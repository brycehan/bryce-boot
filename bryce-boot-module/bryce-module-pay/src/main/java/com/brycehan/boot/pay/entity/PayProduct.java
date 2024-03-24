package com.brycehan.boot.pay.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.base.entity.BaseEntity;
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
