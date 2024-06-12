package com.brycehan.boot.mp.controller;

import com.brycehan.boot.api.system.SysParamApi;
import com.brycehan.boot.api.system.dto.SysParamDto;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 微信公众号菜单API<br>
 * 官方文档：<a href="https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html">https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html</a><br>
 * WxJava开发文档：<a href="https://github.com/Wechat-Group/WxJava/wiki/MP_自定义菜单管理">https://github.com/Wechat-Group/WxJava/wiki/MP_自定义菜单管理</a>
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Slf4j
@Tag(name = "微信公众号菜单API")
@RequestMapping("/mp/menu")
@RestController
@RequiredArgsConstructor
public class MpMenuController {

    private final WxMpService wxMpService;

    private final SysParamApi sysParamApi;

    /**
     * 获取微信公众号菜单
     *
     * @return 响应结果
     */
    @Operation(summary = "获取微信公众号菜单")
    @PreAuthorize("hasAuthority('mp:menu:page')")
    @GetMapping
    public ResponseResult<WxMpMenu> getMenu() throws WxErrorException {
        WxMpMenu wxMpMenu = this.wxMpService.getMenuService().menuGet();
        return ResponseResult.ok(wxMpMenu);
    }

    /**
     * 创建、更新微信公众号菜单
     *
     * @param wxMenu 微信菜单
     * @return 响应结果
     */
    @Operation(summary = "保存/更新微信菜单")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('mp:menu:saveOrUpdate')")
    @PostMapping
    public ResponseResult<Void> saveOrUpdate(@RequestBody WxMenu wxMenu) {

        try {
            if (CollectionUtils.isEmpty(wxMenu.getButtons())) {
                this.wxMpService.getMenuService().menuDelete();
            } else {
                this.wxMpService.getMenuService().menuCreate(wxMenu);
                // 缓存菜单
                String paramKey = DataConstants.WECHAT_MP_MENU;
                SysParamDto sysParamDto = new SysParamDto();
                sysParamDto.setParamKey(paramKey);
                sysParamDto.setParamValue(JsonUtils.writeValueAsString(wxMenu));

                this.sysParamApi.update(sysParamDto);
            }
        } catch (WxErrorException e) {
            WxError error = e.getError();
            log.error("调用API错误，编码：{}，消息：{}", error.getErrorCode(), error.getErrorMsg());
            throw new RuntimeException(error.getErrorMsg());
        }

        return ResponseResult.ok();
    }

    /**
     * 从本地缓存发布微信公众号菜单
     *
     * @return 响应结果
     */
    @Operation(summary = "从本地缓存发布微信公众号菜单")
    @PreAuthorize("hasAuthority('mp:menu:publishByCache')")
    @GetMapping(path = "/publishByCache")
    public ResponseResult<WxMenu> publishByCache() {

        String paramKey = DataConstants.WECHAT_MP_MENU;
        String paramValue = this.sysParamApi.getString(paramKey);
        WxMenu wxMenu = JsonUtils.readValue(paramValue, WxMenu.class);

        return ResponseResult.ok(wxMenu);
    }

}