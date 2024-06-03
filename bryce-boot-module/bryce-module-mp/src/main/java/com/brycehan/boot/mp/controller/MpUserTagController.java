package com.brycehan.boot.mp.controller;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.mp.entity.dto.MpUserTagBatchDto;
import com.brycehan.boot.mp.entity.dto.MpUserTagDto;
import com.brycehan.boot.mp.service.MpUserTagService;
import com.brycehan.boot.mp.entity.vo.MpUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信公众号粉丝标签API
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Tag(name = "微信公众号粉丝标签")
@RequestMapping("/mp/userTag")
@RestController
@RequiredArgsConstructor
public class MpUserTagController {

    private final MpUserTagService mpUserTagService;

    /**
     * 保存微信公众号粉丝标签
     *
     * @param userTagDto 微信公众号粉丝标签Dto
     * @return 响应结果
     */
    @Operation(summary = "保存或更新微信公众号粉丝")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('mp:userTag:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated @RequestBody MpUserTagDto userTagDto) {
        if (userTagDto.getTagId() == null) {
            this.mpUserTagService.createTag(userTagDto.getName());
        } else {
            this.mpUserTagService.updateTag(userTagDto.getTagId(), userTagDto.getName());
        }
        return ResponseResult.ok();
    }

    /**
     * 删除微信公众号粉丝标签
     *
     * @param tagId 标签ID
     * @return 响应结果
     */
    @Operation(summary = "删除微信公众号粉丝")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('mp:userTag:delete')")
    @DeleteMapping(path = "{tagId}")
    public ResponseResult<Void> delete(@Validated @PathVariable Long tagId) {
        this.mpUserTagService.deleteTag(tagId);
        return ResponseResult.ok();
    }

    /**
     * 查询微信公众号粉丝标签列表
     *
     * @return 响应结果
     */
    @Operation(summary = "查询微信公众号粉丝标签列表（在本系统外更新了标签时使用）")
    @PreAuthorize("hasAuthority('mp:userTag:list')")
    @GetMapping(path = "/getTags")
    public ResponseResult<List<WxUserTag>> getTags() {
        List<WxUserTag> tags = this.mpUserTagService.getTags();
        return ResponseResult.ok(tags);
    }

    /**
     * 批量给微信公众号粉丝打标签
     *
     * @param userTagBatchDto 微信公众号粉丝标签批量ID
     * @return 响应结果
     */
    @Operation(summary = "批量给微信公众号粉丝打标签")
    @PreAuthorize("hasAuthority('mp:userTag:save')")
    @GetMapping(path = "/taggingBatch")
    public ResponseResult<MpUserVo> taggingBatch(@Parameter(required = true) @RequestBody MpUserTagBatchDto userTagBatchDto) {
        this.mpUserTagService.taggingBatch(userTagBatchDto.getOpenidList(), userTagBatchDto.getTagId());
        return ResponseResult.ok();
    }

    /**
     * 批量删除微信公众号粉丝标签
     *
     * @param userTagBatchDto 微信公众号粉丝标签批量Dto
     * @return 响应结果
     */
    @Operation(summary = "批量删除微信公众号粉丝标签")
    @PreAuthorize("hasAuthority('mp:userTag:save')")
    @GetMapping(path = "/untaggingBatch")
    public ResponseResult<MpUserVo> untaggingBatch(@Parameter(required = true) @RequestBody MpUserTagBatchDto userTagBatchDto) {
        this.mpUserTagService.untaggingBatch(userTagBatchDto.getOpenidList(), userTagBatchDto.getTagId());
        return ResponseResult.ok();
    }

}