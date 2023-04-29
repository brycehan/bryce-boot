package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.HttpResponseStatusEnum;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.brycehan.boot.system.context.LoginUserContext;
import com.brycehan.boot.system.convert.SysMenuConvert;
import com.brycehan.boot.system.dto.DeleteDto;
import com.brycehan.boot.system.dto.SysMenuDto;
import com.brycehan.boot.system.dto.SysMenuPageDto;
import com.brycehan.boot.system.entity.SysMenu;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.vo.SysMenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统菜单API
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Tag(name = "sysMenu", description = "系统菜单API")
@RequestMapping("/system/menu")
@RestController
@AllArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 保存系统菜单
     *
     * @param sysMenuDto 系统菜单Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统菜单")
//    @Secured("system:menu:add")
    @PostMapping
    public ResponseResult<Void> add(@Parameter(description = "系统菜单", required = true)
                                     @Validated(value = AddGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        this.sysMenuService.save(sysMenuDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统菜单
     *
     * @param sysMenuDto 系统菜单
     * @return 响应结果
     */
    @Operation(summary = "更新系统菜单")
//    @Secured("system:menu:update")
    @PatchMapping
    public ResponseResult<Void> update(@Parameter(description = "系统菜单实体", required = true)
                                       @Validated(value = UpdateGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        this.sysMenuService.update(sysMenuDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统菜单
     *
     * @param deleteDto 系统菜单删除Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统菜单")
    //    @Secured("system:menu:delete")
    @DeleteMapping
    public ResponseResult<Void> delete(@Parameter(description = "系统菜单IDs", required = true)
                                           @Validated @RequestBody DeleteDto deleteDto) {
        // 过滤空数据
        List<String> ids = deleteDto.getIds()
                .stream()
                .filter(StringUtils::isNotBlank)
                .toList();
        if(CollectionUtils.isEmpty(ids)){
            throw BusinessException.responseStatus(HttpResponseStatusEnum.HTTP_BAD_REQUEST);
        }
        // 批量删除
        this.sysMenuService.delete(deleteDto);
        return ResponseResult.ok();
    }



    /**
     * 根据系统菜单 ID 查询系统菜单信息
     *
     * @param id 系统菜单ID
     * @return 响应结果
     */
    @Operation(summary = "根据系统菜单ID查询系统菜单详情")
    //    @Secured("system:menu:info")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysMenuVo> getById(@Parameter(description = "系统菜单ID", required = true)
                                                 @PathVariable String id) {
        SysMenu sysMenu = this.sysMenuService.getById(id);
        return ResponseResult.ok(SysMenuConvert.INSTANCE.convert(sysMenu));
    }

    /**
     * 分页查询
     *
     * @param sysMenuPageDto 查询条件
     * @return 分页系统菜单
     */
    @Operation(summary = "分页查询")
    //    @Secured("system:menu:page")
    @GetMapping(path = "/page")
    public ResponseResult<PageResult<SysMenuVo>> page(@Parameter(description = "查询信息", required = true)
                                                  @Validated @RequestBody SysMenuPageDto sysMenuPageDto) {
        PageResult<SysMenuVo> page = this.sysMenuService.page(sysMenuPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 导航菜单
     *
     * @return 导航菜单
     */
    @Operation(summary = "导航菜单")
    @GetMapping(path = "/nav")
    @Deprecated
    public ResponseResult<List<SysMenu>> nav() {
        List<SysMenu> sysMenuList = this.sysMenuService.getSysMenuListByUserId(LoginUserContext.currentUserId());
        return ResponseResult.ok(sysMenuList);
    }

}

