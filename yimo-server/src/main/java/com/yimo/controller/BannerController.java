package com.yimo.controller;

import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Banner;
import com.yimo.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/banner")
@RequiredArgsConstructor
@Tag(name = "轮播图管理", description = "首页轮播图的增删改查接口")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/list")
    @Operation(summary = "获取启用的轮播图列表", description = "获取所有已启用的轮播图，按sortOrder排序(公开)")
    public Result<List<Banner>> listEnabled() {
        return bannerService.listEnabled();
    }

    @GetMapping("/admin/list")
    @Operation(summary = "分页查询轮播图列表", description = "管理员分页查询所有轮播图，支持关键词搜索、状态筛选")
    public Result<PageResult<Banner>> adminList(@ModelAttribute PageRequest request) {
        return bannerService.list(request);
    }

    @PostMapping
    @Operation(summary = "新增轮播图", description = "管理员新增轮播图")
    public Result<Banner> create(
            @Parameter(description = "轮播图信息") @RequestBody Banner banner) {
        return bannerService.create(banner);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新轮播图", description = "管理员更新指定轮播图信息")
    public Result<Banner> update(
            @Parameter(description = "轮播图ID") @PathVariable Long id,
            @Parameter(description = "轮播图信息") @RequestBody Banner banner) {
        banner.setId(id);
        return bannerService.update(banner);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除轮播图", description = "管理员逻辑删除指定轮播图")
    public Result<Void> delete(
            @Parameter(description = "轮播图ID") @PathVariable Long id) {
        return bannerService.delete(id);
    }
}
