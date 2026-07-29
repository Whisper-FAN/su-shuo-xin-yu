package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.entity.ProductCategory;
import com.yimo.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品分类控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/product/category")
@RequiredArgsConstructor
@Tag(name = "产品分类管理", description = "产品分类的增删改查接口")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/list")
    @Operation(summary = "获取产品分类列表", description = "获取所有产品分类列表，按sortOrder排序")
    public Result<List<ProductCategory>> list() {
        return productCategoryService.listEnabled();
    }

    @PostMapping
    @Operation(summary = "新增产品分类", description = "管理员新增产品分类")
    public Result<ProductCategory> create(
            @Parameter(description = "分类信息") @RequestBody ProductCategory category) {
        return productCategoryService.create(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新产品分类", description = "管理员更新指定产品分类")
    public Result<ProductCategory> update(
            @Parameter(description = "分类ID") @PathVariable Long id,
            @Parameter(description = "分类信息") @RequestBody ProductCategory category) {
        category.setId(id);
        return productCategoryService.update(category);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除产品分类", description = "管理员逻辑删除指定产品分类")
    public Result<Void> delete(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        return productCategoryService.delete(id);
    }
}
