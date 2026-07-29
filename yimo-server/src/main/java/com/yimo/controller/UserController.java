package com.yimo.controller;

import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.User;
import com.yimo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户信息查询、更新、删除等接口")
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    @Operation(summary = "分页查询用户列表", description = "支持关键词搜索、状态筛选的分页查询(管理员)")
    public Result<PageResult<User>> list(@ModelAttribute PageRequest request) {
        return userService.list(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户", description = "查询单个用户的详细信息")
    public Result<User> getById(
            @Parameter(description = "用户ID") @PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户信息", description = "更新用户昵称、头像、手机号等个人信息")
    public Result<User> update(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Parameter(description = "用户信息") @RequestBody User user) {
        user.setId(id);
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "管理员逻辑删除指定用户")
    public Result<Void> delete(
            @Parameter(description = "用户ID") @PathVariable Long id,
            HttpServletRequest request) {
        return userService.delete(id);
    }
}
