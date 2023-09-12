package com.brycehan.boot.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统字典Vo
 *
 * @author Bryce Han
 * @since 2023/09/06
 */
@Data
@Schema(description = "系统字典Vo")
public class SysDictVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典数据列表")
    private List<DictData> datalist = new ArrayList<>();

    @Data
    @Schema(description = "字典数据")
    @AllArgsConstructor
    public static class DictData {

        /**
         * 字典标签
         */
        @Schema(description = "字典标签")
        private String dictLabel;

        /**
         * 字典值
         */
        @Schema(description = "字典值")
        private String dictValue;

        /**
         * 标签属性
         */
        @Schema(description = "标签属性")
        private String labelClass;

    }

}
