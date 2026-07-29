package com.yimo.controller;

import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Gallery;
import com.yimo.service.GalleryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 图库控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@Tag(name = "图库管理", description = "图库资源的增删改查接口")
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping("/list")
    @Operation(summary = "分页查询图库", description = "分页查询图库列表，支持分类筛选")
    public Result<PageResult<Gallery>> list(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "1") Long page,
            @Parameter(description = "每页大小") @RequestParam(required = false, defaultValue = "20") Long size,
            @Parameter(description = "分类") @RequestParam(required = false) String category,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "排序字段") @RequestParam(required = false, defaultValue = "sortOrder") String sortField,
            @Parameter(description = "排序方向") @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        PageRequest request = new PageRequest();
        request.setPage(page);
        request.setSize(size);
        request.setKeyword(keyword);
        request.setSortField(sortField);
        request.setSortOrder(sortOrder);
        if (category != null) request.setKeyword(category);
        return galleryService.list(request);
    }

    @PostMapping
    @Operation(summary = "新增图库资源", description = "管理员新增图库资源")
    public Result<Gallery> create(
            @Parameter(description = "图库资源信息") @RequestBody Gallery gallery) {
        return galleryService.create(gallery);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新图库资源", description = "管理员更新指定图库资源")
    public Result<Gallery> update(
            @Parameter(description = "资源ID") @PathVariable Long id,
            @Parameter(description = "图库资源信息") @RequestBody Gallery gallery) {
        gallery.setId(id);
        return galleryService.update(gallery);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除图库资源", description = "管理员逻辑删除指定图库资源")
    public Result<Void> delete(
            @Parameter(description = "资源ID") @PathVariable Long id) {
        return galleryService.delete(id);
    }
}
