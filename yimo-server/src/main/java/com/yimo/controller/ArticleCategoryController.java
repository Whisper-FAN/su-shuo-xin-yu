package com.yimo.controller;

import com.yimo.common.Result;
import com.yimo.entity.ArticleCategory;
import com.yimo.service.ArticleCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章分类控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "文章分类管理", description = "文章分类的增删改查接口")
public class ArticleCategoryController {

    private final ArticleCategoryService articleCategoryService;

    @GetMapping("/list")
    @Operation(summary = "获取启用的分类列表", description = "获取所有已启用的文章分类，按sortOrder排序(公开)")
    public Result<List<ArticleCategory>> listEnabled() {
        return articleCategoryService.listEnabled();
    }

    @PostMapping
    @Operation(summary = "新增文章分类", description = "管理员新增文章分类")
    public Result<ArticleCategory> create(
            @Parameter(description = "分类信息") @RequestBody ArticleCategory category) {
        return articleCategoryService.create(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新文章分类", description = "管理员更新指定文章分类")
    public Result<ArticleCategory> update(
            @Parameter(description = "分类ID") @PathVariable Long id,
            @Parameter(description = "分类信息") @RequestBody ArticleCategory category) {
        category.setId(id);
        return articleCategoryService.update(category);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章分类", description = "管理员逻辑删除指定文章分类")
    public Result<Void> delete(
            @Parameter(description = "分类ID") @PathVariable Long id) {
        return articleCategoryService.delete(id);
    }
}
