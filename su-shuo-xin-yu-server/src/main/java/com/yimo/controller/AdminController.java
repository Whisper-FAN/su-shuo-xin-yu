package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "管理员后台", description = "管理员后台综合管理接口(需要管理员权限)")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    @Operation(summary = "后台仪表盘", description = "获取后台管理仪表盘综合数据")
    public Result<Map<String, Object>> dashboard() {
        return adminService.getDashboardOverview();
    }

    @GetMapping("/stats/{module}")
    @Operation(summary = "模块统计", description = "获取指定模块的统计数据")
    public Result<Map<String, Object>> moduleStats(
            @Parameter(description = "模块名: user/article/product/zodiac/test/feedback") @PathVariable String module) {
        return adminService.getModuleStats(module);
    }
}
