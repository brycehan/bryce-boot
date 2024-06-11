package com.brycehan.boot.mp.controller;

import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.mp.entity.convert.MpUserConvert;
import com.brycehan.boot.mp.entity.dto.MpUserDto;
import com.brycehan.boot.mp.entity.dto.MpUserPageDto;
import com.brycehan.boot.mp.entity.po.MpUser;
import com.brycehan.boot.mp.entity.vo.MpUserVo;
import com.brycehan.boot.mp.service.MpUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 微信公众号粉丝API
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Tag(name = "微信公众号粉丝")
@RequestMapping("/mp/user")
@RestController
@RequiredArgsConstructor
public class MpUserController {

    private final MpUserService mpUserService;

    /**
     * 保存微信公众号粉丝
     *
     * @param mpUserDto 微信公众号粉丝Dto
     * @return 响应结果
     */
    @Operation(summary = "保存微信公众号粉丝")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('mp:user:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody MpUserDto mpUserDto) {
        this.mpUserService.save(mpUserDto);
        return ResponseResult.ok();
    }

    /**
     * 更新微信公众号粉丝
     *
     * @param mpUserDto 微信公众号粉丝Dto
     * @return 响应结果
     */
    @Operation(summary = "更新微信公众号粉丝")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('mp:user:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody MpUserDto mpUserDto) {
        this.mpUserService.update(mpUserDto);
        return ResponseResult.ok();
    }

    /**
     * 删除微信公众号粉丝
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除微信公众号粉丝")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('mp:user:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.mpUserService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询微信公众号粉丝详情
     *
     * @param openid 微信公众号粉丝ID
     * @return 响应结果
     */
    @Operation(summary = "查询微信公众号粉丝详情")
    @PreAuthorize("hasAuthority('mp:user:info')")
    @GetMapping(path = "/{openid}")
    public ResponseResult<MpUserVo> get(@Parameter(description = "微信公众号粉丝ID", required = true) @PathVariable String openid) {
        MpUser mpUser = this.mpUserService.getById(openid);
        return ResponseResult.ok(MpUserConvert.INSTANCE.convert(mpUser));
    }

    /**
     * 分页查询
     *
     * @param mpUserPageDto 查询条件
     * @return 微信公众号粉丝分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('mp:user:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<MpUserVo>> page(@Validated @RequestBody MpUserPageDto mpUserPageDto) {
        PageResult<MpUserVo> page = this.mpUserService.page(mpUserPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 微信公众号粉丝导出数据
     *
     * @param mpUserPageDto 查询条件
     */
    @Operation(summary = "微信公众号粉丝导出")
    @PreAuthorize("hasAuthority('mp:user:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody MpUserPageDto mpUserPageDto) {
        this.mpUserService.export(mpUserPageDto);
    }

    /**
     * 列表查询
     *
     * @param openIds 查询条件
     * @return 微信公众号粉丝列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('mp:user:list')")
    @PostMapping(path = "/list")
    public ResponseResult<List<MpUserVo>> list(@RequestBody String[] openIds) {
        List<MpUser> users = this.mpUserService.listByIds(Arrays.asList(openIds));
        return ResponseResult.ok(MpUserConvert.INSTANCE.convert(users));
    }

    /**
     * 同步用户列表
     *
     * @return 操作结果
     */
    @Operation(summary = "同步用户列表")
    @PreAuthorize("hasAuthority('mp:user:save')")
    @GetMapping(path = "/syncMpUsers")
    public ResponseResult<?> syncMpUsers() {
        this.mpUserService.syncMpUsers();
        return ResponseResult.ok().setMessage("任务已建立");
    }

}