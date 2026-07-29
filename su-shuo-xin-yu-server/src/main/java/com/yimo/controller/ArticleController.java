package com.yimo.controller;

import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.Article;
import com.yimo.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 文章控制器
 *
 * @author yimo-team
 */
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
@Tag(name = "文章管理", description = "文章的增删改查接口")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/list")
    @Operation(summary = "分页查询已发布文章", description = "分页查询已发布文章列表，支持关键词搜索和分类筛选(公开)")
    public Result<?> list(
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "分类ID") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "当前页码") @RequestParam(required = false, defaultValue = "1") Long page,
            @Parameter(description = "每页大小") @RequestParam(required = false, defaultValue = "10") Long size,
            @Parameter(description = "排序字段") @RequestParam(required = false, defaultValue = "publishTime") String sortField,
            @Parameter(description = "排序方向") @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        PageRequest request = new PageRequest();
        request.setPage(page);
        request.setSize(size);
        request.setKeyword(keyword);
        request.setCategoryId(categoryId);
        request.setSortField(sortField);
        request.setSortOrder(sortOrder);
        request.setStatus(1); // published only
        return articleService.list(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章详情", description = "根据ID获取文章详情，并自动增加浏览次数")
    public Result<?> getById(@Parameter(description = "文章ID") @PathVariable Long id) {
        articleService.incrementViewCount(id);
        return articleService.getById(id);
    }

    @PostMapping
    @Operation(summary = "新增文章", description = "管理员新增文章")
    public Result<Article> create(
            @Parameter(description = "文章信息") @RequestBody Article article) {
        return articleService.create(article);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新文章", description = "管理员更新指定文章")
    public Result<Article> update(
            @Parameter(description = "文章ID") @PathVariable Long id,
            @Parameter(description = "文章信息") @RequestBody Article article) {
        article.setId(id);
        return articleService.update(article);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章", description = "管理员逻辑删除指定文章")
    public Result<Void> delete(
            @Parameter(description = "文章ID") @PathVariable Long id) {
        return articleService.delete(id);
    }
}
