package com.yimo.controller;

import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.ClaySculpture;
import com.yimo.service.ClaySculptureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 泥塑作品控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/clay-sculpture")
@RequiredArgsConstructor
@Tag(name = "泥塑作品管理", description = "泥塑作品的增删改查接口")
public class ClaySculptureController {

    private final ClaySculptureService claySculptureService;

    @GetMapping("/list")
    @Operation(summary = "分页查询泥塑作品", description = "分页查询泥塑作品列表，支持生肖分类和工艺类型筛选")
    public Result<PageResult<ClaySculpture>> list(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "1") Long page,
            @Parameter(description = "每页大小") @RequestParam(required = false, defaultValue = "10") Long size,
            @Parameter(description = "生肖ID") @RequestParam(required = false) Long zodiacId,
            @Parameter(description = "工艺类型") @RequestParam(required = false) String craftType,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "排序字段") @RequestParam(required = false, defaultValue = "sortOrder") String sortField,
            @Parameter(description = "排序方向") @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        PageRequest request = new PageRequest();
        request.setPage(page);
        request.setSize(size);
        request.setKeyword(keyword);
        request.setSortField(sortField);
        request.setSortOrder(sortOrder);
        if (zodiacId != null) request.setCategoryId(zodiacId);
        if (craftType != null) request.setSortField(craftType);
        return claySculptureService.list(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取泥塑作品详情", description = "根据ID获取泥塑作品详细信息")
    public Result<ClaySculpture> getById(
            @Parameter(description = "作品ID") @PathVariable Long id) {
        return claySculptureService.getById(id);
    }

    @PostMapping
    @Operation(summary = "新增泥塑作品", description = "管理员新增泥塑作品")
    public Result<ClaySculpture> create(
            @Parameter(description = "作品信息") @RequestBody ClaySculpture sculpture) {
        return claySculptureService.create(sculpture);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新泥塑作品", description = "管理员更新指定泥塑作品")
    public Result<ClaySculpture> update(
            @Parameter(description = "作品ID") @PathVariable Long id,
            @Parameter(description = "作品信息") @RequestBody ClaySculpture sculpture) {
        sculpture.setId(id);
        return claySculptureService.update(sculpture);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除泥塑作品", description = "管理员逻辑删除指定泥塑作品")
    public Result<Void> delete(
            @Parameter(description = "作品ID") @PathVariable Long id) {
        return claySculptureService.delete(id);
    }
}
