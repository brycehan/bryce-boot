package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.dto.LoginDto;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.vo.LoginVo;
import com.brycehan.boot.common.constant.JwtConstants;
import com.brycehan.boot.framework.security.TokenUtils;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.context.LoginUserContext;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.AuthService;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysUserService;
import com.brycehan.boot.system.vo.SysMenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 认证API
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "auth", description = "认证API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final SysUserService sysUserService;

    private final SysMenuService sysMenuService;

    /**
     * 账号登录
     *
     * @param loginDto 登录dto
     * @return 响应结果
     */
    @Operation(summary = "登录")
    @PostMapping(path = "/loginByAccount")
    public ResponseEntity<ResponseResult<LoginVo>> loginByAccount(@Validated @RequestBody LoginDto loginDto) {

        String jwt = authService.loginByAccount(loginDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtConstants.AUTHORIZATION_HEADER, JwtConstants.TOKEN_PREFIX.concat(jwt));

        LoginVo loginVo = LoginVo.builder()
                .token(JwtConstants.TOKEN_PREFIX.concat(jwt))
                .build();
        
        return new ResponseEntity<>(ResponseResult.ok(loginVo), httpHeaders, HttpStatus.OK);
    }

    /**
     * 获取用户权限标识
     *
     * @return 响应结果
     */
    @Operation(summary = "获取用户权限标识", description = "用户权限标识集合")
    @GetMapping(path = "/authority")
    public ResponseResult<Set<String>> authority() {
        LoginUser loginUser = LoginUserContext.currentUser();
        Set<String> authoritySet = this.sysMenuService.findAuthority(loginUser);
        return ResponseResult.ok(authoritySet);
    }

    /**
     * 查询系统登录用户详情
     *
     * @return 响应结果
     */
    @Operation(summary = "查询系统登录用户详情")
    @GetMapping(path = "/currentUser")
    public ResponseResult<SysUser> currentUser() {
        LoginUser loginUser = LoginUserContext.currentUser();
        SysUser sysUser = this.sysUserService.getById(loginUser);
            sysUser.setPassword(null);
            // 角色权限
            Set<String> roleAuthoritySet = this.authService.getRoleAuthority(loginUser);
            // 菜单权限
            Set<String> menuAuthoritySet = this.authService.getMenuAuthority(loginUser);
            sysUser.setRoles(roleAuthoritySet);
            sysUser.setAuthoritySet(menuAuthoritySet);
            return ResponseResult.ok(sysUser);
    }

    /**
     * 获取路由信息
     *
     * @return 路由列表
     */
    @Operation(summary = "获取菜单列表")
    @GetMapping(path =  "/nav")
    public ResponseResult<List<SysMenuVo>> nav() {
        List<SysMenuVo> list = this.sysMenuService.getMenuTreeList(LoginUserContext.currentUser(), "M");

        return ResponseResult.ok(list);
    }

    /**
     * 退出登录
     *
     * @return 响应结果
     */
    @Operation(summary = "退出登录")
    @GetMapping(path = "/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        this.authService.logout(TokenUtils.getAccessToken(request));
        return ResponseResult.ok();
    }

}
