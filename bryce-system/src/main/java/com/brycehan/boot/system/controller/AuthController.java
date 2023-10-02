package com.brycehan.boot.system.controller;

import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.framework.security.TokenUtils;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.context.LoginUserContext;
import com.brycehan.boot.system.service.*;
import com.brycehan.boot.system.vo.SysMenuVo;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.common.base.dto.LoginDto;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.vo.LoginVo;
import com.brycehan.boot.common.constant.JwtConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 登录控制器
 *
 * @author Bryce Han
 * @since 2022/5/10
 */
@Slf4j
@Tag(name = "auth", description = "登录API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * jwt密钥
     */
    @Value("${bryce.jwt.secret}")
    private String jwtSecret;

    private final AuthService authService;

    private final SysUserService sysUserService;

    private final SysMenuService sysMenuService;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 登录
     *
     * @param loginDto 登录dto
     * @return 响应结果
     */
    @Operation(summary = "登录")
    @PostMapping(path = "/login")
    public ResponseResult<LoginVo> login(@Validated @RequestBody LoginDto loginDto) {

        String jwt = authService.login(loginDto);

        LoginVo loginVo = LoginVo.builder()
                .token(JwtConstants.TOKEN_PREFIX.concat(jwt))
                .type(loginDto.getType())
                .build();

        return ResponseResult.ok(loginVo);
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
