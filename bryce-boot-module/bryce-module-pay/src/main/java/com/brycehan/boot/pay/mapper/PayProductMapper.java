package com.brycehan.boot.pay.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.pay.entity.po.PayProduct;
import org.apache.ibatis.annotations.Mapper;

/**
* 商品Mapper接口
*
* @author Bryce Han
* @since 2024/02/28
*/
@Mapper
public interface PayProductMapper extends BryceBaseMapper<PayProduct> {

}
