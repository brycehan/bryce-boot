package com.brycehan.boot.wechat.controller;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.system.dto.SysParamDto;
import com.brycehan.boot.system.service.SysParamService;
import com.brycehan.boot.system.vo.SysParamVo;
import com.brycehan.boot.wechat.convert.WechatMenuConvert;
import com.brycehan.boot.wechat.dto.WechatMenuItemDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信菜单API
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Slf4j
@Tag(name = "微信菜单", description = "wechatMenu")
@RequestMapping("/wechat/menu")
@RestController
@RequiredArgsConstructor
public class WechatMenuController {

    private final WxMpService wxMpService;

    private final SysParamService sysParamService;

    /**
     * 更新微信菜单
     *
     * @param appId 应用ID
     * @param itemDtoList 微信菜单Dto
     * @return 响应结果
     */
    @Operation(summary = "保存/更新微信菜单")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('wechat:menu:saveOrUpdate')")
    @PutMapping(path = "/{appId}")
    public ResponseResult<Void> saveOrUpdate(
            @PathVariable String appId,
            @Validated(value = UpdateGroup.class) @RequestBody List<WechatMenuItemDto> itemDtoList) {

        if(itemDtoList.size() > 3) {
            throw new RuntimeException("主菜单数量不能超过3个");
        }

        WxMpMenuService menuService = this.wxMpService.switchoverTo(appId).getMenuService();

        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> wxMenuButtons = WechatMenuConvert.INSTANCE.convert2Buttons(itemDtoList);
        wxMenu.setButtons(wxMenuButtons);

        String paramKey = CommonConstants.WECHAT_PARAM.concat(appId);
        boolean exists = this.sysParamService.exists(paramKey);
        if(exists) {
            // 更新
            SysParamVo sysParamVo = this.sysParamService.getByParamKey(paramKey);

            SysParamDto sysParamDto = new SysParamDto();
            sysParamDto.setId(sysParamVo.getId());
            sysParamDto.setParamKey(paramKey);
            sysParamDto.setParamValue(JsonUtils.writeValueAsString(wxMenuButtons));

            this.sysParamService.update(sysParamDto);
        } else {
            // 添加
            SysParamDto sysParamDto = new SysParamDto();
            sysParamDto.setParamName("公众号AppID（".concat(appId).concat("）菜单"));
            sysParamDto.setParamKey(CommonConstants.WECHAT_PARAM.concat(appId));
            sysParamDto.setParamValue(JsonUtils.writeValueAsString(itemDtoList));
            this.sysParamService.save(sysParamDto);
        }

        try {
            menuService.menuCreate(wxMenu);
        } catch (WxErrorException e) {
            log.error(e.getMessage());
            throw new RuntimeException("设置微信菜单失败");
        }

        return ResponseResult.ok();
    }

    /**
     * 清空线上菜单
     *
     * @param appId 微信AppID
     * @return 响应结果
     */
    @Operation(summary = "清空线上菜单")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('wechat:menu:delete')")
    @DeleteMapping(path = "/{appId}")
    public ResponseResult<Void> delete(@PathVariable String appId) {
        try {
            WxMpMenuService menuService = this.wxMpService.switchoverTo(appId).getMenuService();
            menuService.menuDelete();
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }

        return ResponseResult.ok();
    }

    /**
     * 查询微信菜单详情
     *
     * @param appId AppID
     * @return 响应结果
     */
    @Operation(summary = "查询微信菜单详情")
    @PreAuthorize("hasAuthority('wechat:menu:info')")
    @GetMapping(path = "/{appId}")
    public ResponseResult<List<?>> get(@Parameter(description = "AppID", required = true) @PathVariable String appId) {


            String paramKey = CommonConstants.WECHAT_PARAM.concat(appId);
            boolean exists = this.sysParamService.exists(paramKey);
            if(exists) {
                String paramValue = this.sysParamService.getString(paramKey);
                return ResponseResult.ok((List<?>) JsonUtils.readValue(paramValue, List.class));
            }

        return ResponseResult.ok(new ArrayList<>());
    }

}