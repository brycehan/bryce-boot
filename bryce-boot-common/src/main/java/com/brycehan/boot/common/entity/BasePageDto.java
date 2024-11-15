package com.brycehan.boot.common.entity;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.entity.convert.OrderItemConvert;
import com.brycehan.boot.common.util.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础分页数据传输对象
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Data
public abstract class BasePageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(BasePageDto.class);

    /**
     * 当前页数（从1开始计算）
     */
    @Schema(description = "当前页数（从1开始计算）")
    @Range(min = 1, message = "页数最小值为1")
    @NotNull(message = "页码不能为空")
    private Integer current;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数")
    @Range(min = 1, max = 1000, message = "每页条数，取值范围在1-1000")
    @NotNull(message = "每页条数不能为空")
    private Integer size;

    /**
     * 排序项
     */
    @Schema(description = "排序项")
    @Valid
    private List<OrderItemDto> orderItems;

    /**
     * 获取分页对象
     *
     * @return 分页对象
     */
    public  <T> IPage<T> toPage() {
        Page<T> page = new Page<>(this.current, this.size);
        log.debug("ServiceImpl.getPage 参数：{}", this);

        List<OrderItem> orderItems = new ArrayList<>();
        // 处理排序参数
        if (CollectionUtils.isNotEmpty(this.orderItems)) {
            // 过滤无效排序参数
            List<OrderItemDto> itemDtoList = this.orderItems.stream()
                    .filter(orderItem -> hasEntityField(this.getClass(), orderItem.getColumn()))
                    .toList();

            log.debug("排序参数: {}", JsonUtils.writeValueAsString(itemDtoList));

            if (CollectionUtils.isNotEmpty(itemDtoList)) {
                // 驼峰转下划线命名
                itemDtoList.forEach(orderItem -> orderItem.setColumn(NamingCase.toUnderlineCase(orderItem.getColumn())));
                orderItems.addAll(OrderItemConvert.INSTANCE.convert(itemDtoList));
            }
        }

        // 无参数时，若有sort字段，默认按sort排序升序
        if (CollectionUtils.isEmpty(orderItems) && hasEntityField(this.getClass(), DataConstants.DEFAULT_SORT_COLUMN)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(DataConstants.DEFAULT_SORT_COLUMN);
            orderItem.setAsc(DataConstants.DEFAULT_SORT_IS_ASC);

            orderItems.add(orderItem);
        }

        // 默认按id降序排序
        if (CollectionUtils.isEmpty(orderItems)) {
            orderItems.add(OrderItem.desc("id"));
        }

        page.addOrder(orderItems);

        return page;
    }

        /**
     * 判断一个类中是否含有某个属性字段
     *
     * @param pageDtoClazz pageDto类对象
     * @param fieldName 属性名称
     * @return 布尔值
     */
    public boolean hasEntityField(Class<?> pageDtoClazz, String fieldName) {
        String clazzName = pageDtoClazz.getName();
        if(clazzName.endsWith("PageDto")) {
            clazzName = clazzName.substring(0, clazzName.indexOf("PageDto"));
        }
        clazzName = clazzName.replace(".dto.", ".entity.");
        try {
            return ReflectUtil.hasField(Class.forName(clazzName), fieldName);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 转换为JSON字符串
     *
     * @return JSON字符串
     */
    public String toJson(){
        return JSONUtil.toJsonStr(this);
    }

}
