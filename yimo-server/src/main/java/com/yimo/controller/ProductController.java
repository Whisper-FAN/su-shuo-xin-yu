package com.yimo.controller;

import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Product;
import com.yimo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 产品控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "产品管理", description = "文创产品的增删改查接口")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    @Operation(summary = "分页查询产品", description = "分页查询产品列表，支持分类筛选、关键词搜索和排序(公开)")
    public Result<PageResult<Product>> list(
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "1") Long page,
            @Parameter(description = "每页大小") @RequestParam(required = false, defaultValue = "10") Long size,
            @Parameter(description = "分类ID") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "排序字段") @RequestParam(required = false, defaultValue = "sortOrder") String sortField,
            @Parameter(description = "排序方向") @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        PageRequest request = new PageRequest();
        request.setPage(page);
        request.setSize(size);
        request.setCategoryId(categoryId);
        request.setKeyword(keyword);
        request.setSortField(sortField);
        request.setSortOrder(sortOrder);
        return productService.list(request);
    }

    @GetMapping("/hot")
    @Operation(summary = "获取热门产品", description = "获取标记为热门的推荐产品列表(公开)")
    public Result<?> hot(@Parameter(description = "数量限制") @RequestParam(defaultValue = "8") Integer limit) {
        return productService.listHot(limit);
    }

    @GetMapping("/recommend")
    @Operation(summary = "获取推荐产品", description = "获取推荐的文创产品列表(公开)")
    public Result<?> recommend(@Parameter(description = "数量限制") @RequestParam(defaultValue = "8") Integer limit) {
        return productService.listRecommended(limit);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取产品详情", description = "根据ID获取产品详情，并自动增加浏览次数")
    public Result<?> getById(@Parameter(description = "产品ID") @PathVariable Long id) {
        productService.incrementViewCount(id);
        return productService.getById(id);
    }

    @PostMapping
    @Operation(summary = "新增产品", description = "管理员新增产品")
    public Result<Product> create(
            @Parameter(description = "产品信息") @RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新产品", description = "管理员更新指定产品")
    public Result<Product> update(
            @Parameter(description = "产品ID") @PathVariable Long id,
            @Parameter(description = "产品信息") @RequestBody Product product) {
        product.setId(id);
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除产品", description = "管理员逻辑删除指定产品")
    public Result<Void> delete(
            @Parameter(description = "产品ID") @PathVariable Long id) {
        return productService.delete(id);
    }
}
