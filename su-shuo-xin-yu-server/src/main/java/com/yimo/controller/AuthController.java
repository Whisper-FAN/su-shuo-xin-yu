package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 认证控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "登录、登出、Token刷新")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "使用用户名和密码登录，返回JWT Token")
    public Result<Map<String, Object>> login(
            @Parameter(description = "用户名") @RequestParam String username,
            @Parameter(description = "密码") @RequestParam String password) {
        return authService.login(username, password);
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新Token", description = "使用旧Token换取新Token")
    public Result<Map<String, Object>> refreshToken(
            @Parameter(description = "旧Token") @RequestParam String token) {
        return authService.refreshToken(token);
    }

    @GetMapping("/current")
    @Operation(summary = "获取当前用户", description = "根据Token获取当前登录用户信息")
    public Result<Map<String, Object>> currentUser(
            @Parameter(description = "Token") @RequestHeader("Authorization") String authorization) {
        String token = authorization.startsWith("Bearer ") ? authorization.substring(7) : authorization;
        return authService.currentUser(token);
    }
}
