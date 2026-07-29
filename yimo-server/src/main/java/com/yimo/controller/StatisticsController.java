package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.entity.UserBehavior;
import com.yimo.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 统计控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Tag(name = "数据统计", description = "平台数据统计与用户行为记录接口")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/dashboard")
    @Operation(summary = "获取仪表盘统计数据", description = "获取平台公开统计数据：用户数、测试数、内容数等(公开)")
    public Result<Map<String, Object>> dashboard() {
        return statisticsService.getDashboardStats();
    }

    @PostMapping("/behavior")
    @Operation(summary = "记录用户行为", description = "记录用户浏览、点击等行为数据(公开)")
    public Result<Void> recordBehavior(
            @Parameter(description = "行为数据") @RequestBody UserBehavior behavior,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId != null) {
            behavior.setUserId(userId);
        }
        return statisticsService.recordUserBehavior(behavior);
    }

    @GetMapping("/daily")
    @Operation(summary = "获取每日统计数据", description = "按日期范围获取每日统计详情(管理员)")
    public Result<?> daily(@Parameter(description = "开始日期 yyyy-MM-dd") @RequestParam String startDate,
                           @Parameter(description = "结束日期 yyyy-MM-dd") @RequestParam String endDate) {
        return statisticsService.getDailyReport(startDate, endDate);
    }
}
