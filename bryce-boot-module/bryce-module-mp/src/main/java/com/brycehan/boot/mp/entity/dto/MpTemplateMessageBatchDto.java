package com.brycehan.boot.mp.entity.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.List;
import java.util.Map;

/**
 * 微信公众号模板消息Dto
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "微信公众号模板消息Dto")
public class MpTemplateMessageBatchDto extends BaseDto {

    /**
     * 用户筛选条件参数
     */
    @Schema(description = "用户筛选条件参数")
    @NotNull(message = "用户筛选条件参数为能为空")
    private Map<String, Object> mpUserFilterParams;

    /**
     * 模版ID
     */
    @Schema(description = "模版ID")
    @NotEmpty(message = "模版ID不能为空")
    @Size(max = 50, groups = {SaveGroup.class, UpdateGroup.class})
    private String templateId;

    /**
     * 链接
     */
    @Schema(description = "链接")
    @Size(max = 255, groups = {SaveGroup.class, UpdateGroup.class})
    private String url;

    /**
     * 小程序
     */
    @Schema(description = "小程序")
    private WxMpTemplateMessage.MiniProgram miniProgram;

    /**
     * 模板消息数据
     */
    @Schema(description = "模板消息数据不能为空")
    private List<WxMpTemplateData> data;

}
