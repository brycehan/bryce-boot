package com.brycehan.boot.api.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.brycehan.boot.common.base.dto.JwtTokenDto;
import com.brycehan.boot.common.base.dto.LoginDto;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.vo.LoginVo;
import com.brycehan.boot.common.base.vo.MenuVo;
import com.brycehan.boot.common.constant.JwtConstants;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.framework.service.AuthenticationService;
import com.brycehan.boot.system.context.LoginUserContext;
import com.brycehan.boot.system.convert.SysMenuConvert;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysPermissionService;
import com.brycehan.boot.system.service.SysUserService;
import com.brycehan.boot.system.vo.SysMenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 登录控制器
 *
 * @author Bryce Han
 * @since 2022/5/10
 */
@Slf4j
@SaIgnore
@Tag(name = "auth", description = "登录API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    /**
     * jwt密钥
     */
    @Value("${bryce.jwt.secret}")
    private String jwtSecret;

    private final AuthenticationService authenticationService;

    private final SysUserService sysUserService;

    private final SysPermissionService sysPermissionService;

    private final SysMenuService sysMenuService;

    /**
     * 登录
     *
     * @param loginDto 登录dto
     * @return 响应结果
     */
    @Operation(summary = "登录")
    @PostMapping(path = "/login")
    public ResponseResult<LoginVo> login(@Validated @RequestBody LoginDto loginDto) {

        String jwt = authenticationService.login(loginDto);

        LoginVo loginVo = LoginVo.builder()
                .token(JwtConstants.TOKEN_PREFIX.concat(jwt))
                .type(loginDto.getType())
                .build();
        return ResponseResult.ok(loginVo);
    }

    /**
     * 校验token有效期
     *
     * @param tokenDto 令牌dto
     * @return 响应结果
     */
    @Operation(summary = "校验token有效期", description = "true：token是有效的，false：token是无效的")
    @PostMapping(path = "/validateToken")
    public ResponseResult<Boolean> validateToken(@Parameter(description = "Jwt token数据传输对象", required = true)
                                                 @Validated @RequestBody JwtTokenDto tokenDto) {
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            verifier.verify(tokenDto.getToken());
            // 获取有效时间分钟数
            DecodedJWT jwt = JWT.decode(tokenDto.getToken());
            Date expireTime = jwt.getClaim(PublicClaims.EXPIRES_AT).asDate();
            LocalDateTime expiredTime = DateTimeUtils.toLocalDateTime(expireTime);
            long minutes = Duration.between(LocalDateTime.now(), expiredTime).toMinutes();

            return ResponseResult.ok(Boolean.TRUE, "有效分钟数".concat(String.valueOf(minutes)));
        } catch (JWTVerificationException e) {
            return ResponseResult.ok(Boolean.FALSE);
        }
    }

    /**
     * 查询系统登录用户详情
     *
     * @return 响应结果
     */
    @Operation(summary = "查询系统登录用户详情")
    @GetMapping(path = "/currentUser")
    public ResponseResult<SysUser> currentUser() {
            SysUser sysUser = this.sysUserService.getById(LoginUserContext.currentUserId());
            sysUser.setPassword(null);
            // 角色权限
            Set<String> rolePermission = this.sysPermissionService.getRolePermission(sysUser);
            // 菜单权限
            Set<String> menuPermission = this.sysPermissionService.getMenuPermission(sysUser);
            sysUser.setRoles(rolePermission);
            sysUser.setPermissions(menuPermission);
            return ResponseResult.ok(sysUser);
    }

    /**
     * 获取路由信息
     *
     * @return 路由列表
     */
    @Operation(summary = "获取路由信息")
    @GetMapping(path = { "/menus"})
    public ResponseResult<List<MenuVo>> routes() {
        List<SysMenuVo> menuVos = Stream.of(LoginUserContext.currentUserId())
                .map(this.sysMenuService::selectMenuTreeByUserId)
                .flatMap(Collection::stream)
                .toList();
        System.out.println(SysMenuConvert.INSTANCE.convertMenu(menuVos));
        List<MenuVo> menus = Stream.of(LoginUserContext.currentUserId())
                .map(this.sysMenuService::selectMenuTreeByUserId)
                .map(this.sysMenuService::buildMenus)
                .flatMap(Collection::stream)
                .toList();

        return ResponseResult.ok(menus);
    }

    /**
     * 退出登录
     *
     * @return 响应结果
     */
    @Operation(summary = "退出登录")
    @PostMapping(path = "/logout")
    public ResponseResult<Void> logout() {
        return ResponseResult.ok();
    }

}
