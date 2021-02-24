package com.brycehan.boot.core.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Bryce Han
 * @date 2021/2/24
 */
@Data
public class PageVO {
    @NotNull
    @Min(1)
    private Long current;

    @NotNull
    @Min(1)
    @Max(50)
    private Long size;
}
