package com.brycehan.boot.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.wechat.config.WechatAppType;
import com.brycehan.boot.wechat.config.WechatConfig;
import com.brycehan.boot.wechat.convert.WechatAppConvert;
import com.brycehan.boot.wechat.dto.WechatAppDto;
import com.brycehan.boot.wechat.dto.WechatAppPageDto;
import com.brycehan.boot.wechat.entity.WechatApp;
import com.brycehan.boot.wechat.service.WechatAppService;
import com.brycehan.boot.wechat.vo.WechatAppVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信应用API
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Tag(name = "微信应用", description = "wechatApp")
@RequestMapping("/wechat/app")
@RestController
@RequiredArgsConstructor
public class WechatAppController {

    private final WechatAppService wechatAppService;

    private final WxMpService wxMpService;

    private final WxMaService wxMaService;

    /**
     * 保存微信应用
     *
     * @param wechatAppDto 微信应用Dto
     * @return 响应结果
     */
    @Operation(summary = "保存微信应用")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('wechat:app:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody WechatAppDto wechatAppDto) {

        this.wechatAppService.save(wechatAppDto);

        configStorage(wechatAppDto);
        return ResponseResult.ok();
    }

    /**
     * 更新微信应用
     *
     * @param wechatAppDto 微信应用Dto
     * @return 响应结果
     */
    @Operation(summary = "更新微信应用")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('wechat:app:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody WechatAppDto wechatAppDto) {
        // 更新
        this.wechatAppService.update(wechatAppDto);

        configStorage(wechatAppDto);
        return ResponseResult.ok();
    }

    private void configStorage(WechatAppDto wechatAppDto) {
        // 公众号存储
        if(WechatAppType.mp.name().equals(wechatAppDto.getType())) {
            WxMpConfigStorage wxMpConfigStorage = WechatConfig.createMp(WechatAppConvert.INSTANCE.convert(wechatAppDto));
            // 更新微信配置
            this.wxMpService.addConfigStorage(wechatAppDto.getAppId(), wxMpConfigStorage);
        } else {
            // 小程序存储
            WxMaConfig wxMaConfigStorage = WechatConfig.createMa(WechatAppConvert.INSTANCE.convert(wechatAppDto));
            // 更新微信配置
            this.wxMaService.addConfig(wechatAppDto.getAppId(), wxMaConfigStorage);
        }
    }

    /**
     * 删除微信应用
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除微信应用")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('wechat:app:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.wechatAppService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询微信应用详情
     *
     * @param id 微信应用ID
     * @return 响应结果
     */
    @Operation(summary = "查询微信应用详情")
    @PreAuthorize("hasAuthority('wechat:app:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<WechatAppVo> get(@Parameter(description = "微信应用ID", required = true) @PathVariable Long id) {
        WechatApp wechatApp = this.wechatAppService.getById(id);
        return ResponseResult.ok(WechatAppConvert.INSTANCE.convert(wechatApp));
    }

    /**
     * 分页查询
     *
     * @param wechatAppPageDto 查询条件
     * @return 微信应用分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('wechat:app:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<WechatAppVo>> page(@Validated @RequestBody WechatAppPageDto wechatAppPageDto) {
        PageResult<WechatAppVo> page = this.wechatAppService.page(wechatAppPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 微信应用导出数据
     *
     * @param wechatAppPageDto 查询条件
     */
    @Operation(summary = "微信应用导出")
    @PreAuthorize("hasAuthority('wechat:app:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody WechatAppPageDto wechatAppPageDto) {
        this.wechatAppService.export(wechatAppPageDto);
    }

    /**
     * 列表查询
     *
     * @param wechatAppDto 列表条件
     * @return 微信应用列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('wechat:app:list')")
    @PostMapping(path = "/list")
    public ResponseResult<List<WechatAppVo>> list(@Validated @RequestBody WechatAppDto wechatAppDto) {
        List<WechatAppVo> page = this.wechatAppService.list(wechatAppDto);
        return ResponseResult.ok(page);
    }

}