package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.ArticleCategory;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.ArticleCategoryMapper;
import com.yimo.service.ArticleCategoryService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章分类服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    private final ArticleCategoryMapper articleCategoryMapper;

    @Override
    public Result<ArticleCategory> getById(Long id) {
        ArticleCategory category = articleCategoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(404, "文章分类不存在");
        }
        return Result.success(category);
    }

    @Override
    public Result<PageResult<ArticleCategory>> list(PageRequest request) {
        Page<ArticleCategory> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<ArticleCategory> wrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.like(ArticleCategory::getName, request.getKeyword());
        }
        if (request.getStatus() != null) {
            wrapper.eq(ArticleCategory::getStatus, request.getStatus());
        }
        wrapper.orderByAsc(ArticleCategory::getSortOrder);

        Page<ArticleCategory> result = articleCategoryMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<List<ArticleCategory>> listEnabled() {
        List<ArticleCategory> categories = articleCategoryMapper.selectList(
                new LambdaQueryWrapper<ArticleCategory>()
                        .eq(ArticleCategory::getStatus, 1)
                        .orderByAsc(ArticleCategory::getSortOrder));
        return Result.success(categories);
    }

    @Override
    public Result<ArticleCategory> create(ArticleCategory category) {
        articleCategoryMapper.insert(category);
        log.info("创建文章分类成功: id={}, name={}", category.getId(), category.getName());
        return Result.success(category);
    }

    @Override
    public Result<ArticleCategory> update(ArticleCategory category) {
        ArticleCategory existing = articleCategoryMapper.selectById(category.getId());
        if (existing == null) {
            throw new BusinessException(404, "文章分类不存在");
        }
        articleCategoryMapper.updateById(category);
        log.info("更新文章分类成功: id={}", category.getId());
        return Result.success(category);
    }

    @Override
    public Result<Void> delete(Long id) {
        ArticleCategory existing = articleCategoryMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "文章分类不存在");
        }
        articleCategoryMapper.deleteById(id);
        log.info("删除文章分类成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        ArticleCategory category = articleCategoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(404, "文章分类不存在");
        }
        category.setStatus(status);
        articleCategoryMapper.updateById(category);
        log.info("更新文章分类状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
