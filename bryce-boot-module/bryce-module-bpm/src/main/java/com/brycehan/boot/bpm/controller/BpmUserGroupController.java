package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.bpm.entity.convert.BpmUserGroupConvert;
import com.brycehan.boot.bpm.entity.dto.BpmUserGroupDto;
import com.brycehan.boot.bpm.entity.dto.BpmUserGroupPageDto;
import com.brycehan.boot.bpm.entity.po.BpmUserGroup;
import com.brycehan.boot.bpm.service.BpmUserGroupService;
import com.brycehan.boot.bpm.entity.vo.BpmUserGroupVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户组API
 *
 * @author Bryce Han
 * @since 2025/03/08
 */
@Tag(name = "用户组", description = "bpmUserGroup")
@RequestMapping("/bpm/user-group")
@RestController
@RequiredArgsConstructor
public class BpmUserGroupController {

    private final BpmUserGroupService bpmUserGroupService;

    /**
     * 保存用户组
     *
     * @param bpmUserGroupDto 用户组Dto
     * @return 响应结果
     */
    @Operation(summary = "保存用户组")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('bpm:user-group:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody BpmUserGroupDto bpmUserGroupDto) {
        bpmUserGroupService.save(bpmUserGroupDto);
        return ResponseResult.ok();
    }

    /**
     * 更新用户组
     *
     * @param bpmUserGroupDto 用户组Dto
     * @return 响应结果
     */
    @Operation(summary = "更新用户组")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('bpm:user-group:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody BpmUserGroupDto bpmUserGroupDto) {
        bpmUserGroupService.update(bpmUserGroupDto);
        return ResponseResult.ok();
    }

    /**
     * 删除用户组
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除用户组")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('bpm:user-group:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        bpmUserGroupService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询用户组详情
     *
     * @param id 用户组ID
     * @return 响应结果
     */
    @Operation(summary = "查询用户组详情")
    @PreAuthorize("@auth.hasAuthority('bpm:user-group:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<BpmUserGroupVo> get(@Parameter(description = "用户组ID", required = true) @PathVariable Long id) {
        BpmUserGroup bpmUserGroup = bpmUserGroupService.getById(id);
        return ResponseResult.ok(BpmUserGroupConvert.INSTANCE.convert(bpmUserGroup));
    }

    /**
     * 用户组分页查询
     *
     * @param bpmUserGroupPageDto 查询条件
     * @return 用户组分页列表
     */
    @Operation(summary = "用户组分页查询")
    @PreAuthorize("@auth.hasAuthority('bpm:user-group:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<BpmUserGroupVo>> page(@Validated @RequestBody BpmUserGroupPageDto bpmUserGroupPageDto) {
        PageResult<BpmUserGroupVo> page = bpmUserGroupService.page(bpmUserGroupPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 用户组导出数据
     *
     * @param bpmUserGroupPageDto 查询条件
     */
    @Operation(summary = "用户组导出")
    @PreAuthorize("@auth.hasAuthority('bpm:user-group:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody BpmUserGroupPageDto bpmUserGroupPageDto) {
        bpmUserGroupService.export(bpmUserGroupPageDto);
    }

    /**
     * 用户组列表数据
     *
     * @param bpmUserGroupPageDto 查询条件
     */
    @Operation(summary = "用户组列表")
    @GetMapping(path = "/simple-list")
    public ResponseResult<List<BpmUserGroupVo>> simpleList(@Validated BpmUserGroupPageDto bpmUserGroupPageDto) {
        List<BpmUserGroup> bpmUserGroups = bpmUserGroupService.simpleList(bpmUserGroupPageDto);
        return ResponseResult.ok(BpmUserGroupConvert.INSTANCE.convert(bpmUserGroups));
    }

}