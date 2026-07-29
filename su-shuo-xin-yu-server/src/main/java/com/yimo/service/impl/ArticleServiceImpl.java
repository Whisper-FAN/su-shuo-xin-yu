package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Article;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.ArticleMapper;
import com.yimo.service.ArticleService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 文章服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    @Override
    public Result<Article> getById(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(404, "文章不存在");
        }
        // Increment view count
        article.setViewCount(article.getViewCount() != null ? article.getViewCount() + 1 : 1L);
        articleMapper.updateById(article);
        return Result.success(article);
    }

    @Override
    public Result<PageResult<Article>> list(PageRequest request) {
        Page<Article> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();

        // Keyword search on title
        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.like(Article::getTitle, request.getKeyword());
        }

        // Category filter
        if (request.getCategoryId() != null) {
            wrapper.eq(Article::getCategoryId, request.getCategoryId());
        }

        // Status filter
        if (request.getStatus() != null) {
            wrapper.eq(Article::getStatus, request.getStatus());
        }

        // Sort
        wrapper.orderByDesc(Article::getIsTop);
        if (StrUtil.isNotBlank(request.getSortField())) {
            boolean isAsc = "asc".equalsIgnoreCase(request.getSortOrder());
            wrapper.orderBy(true, isAsc, Article::getCreateTime);
        } else {
            wrapper.orderByDesc(Article::getCreateTime);
        }

        Page<Article> result = articleMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<Article> create(Article article) {
        if (article.getViewCount() == null) {
            article.setViewCount(0L);
        }
        if (article.getLikeCount() == null) {
            article.setLikeCount(0L);
        }
        articleMapper.insert(article);
        log.info("创建文章成功: id={}, title={}", article.getId(), article.getTitle());
        return Result.success(article);
    }

    @Override
    public Result<Article> update(Article article) {
        Article existing = articleMapper.selectById(article.getId());
        if (existing == null) {
            throw new BusinessException(404, "文章不存在");
        }
        articleMapper.updateById(article);
        log.info("更新文章成功: id={}", article.getId());
        return Result.success(article);
    }

    @Override
    public Result<Void> delete(Long id) {
        Article existing = articleMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "文章不存在");
        }
        articleMapper.deleteById(id);
        log.info("删除文章成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> incrementViewCount(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(404, "文章不存在");
        }
        article.setViewCount(article.getViewCount() != null ? article.getViewCount() + 1 : 1L);
        articleMapper.updateById(article);
        return Result.success();
    }

    @Override
    public Result<Void> incrementLikeCount(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(404, "文章不存在");
        }
        article.setLikeCount(article.getLikeCount() != null ? article.getLikeCount() + 1 : 1L);
        articleMapper.updateById(article);
        return Result.success();
    }

    @Override
    public Result<Void> updateTopStatus(Long id, Integer isTop) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(404, "文章不存在");
        }
        article.setIsTop(isTop);
        articleMapper.updateById(article);
        log.info("更新文章置顶状态成功: id={}, isTop={}", id, isTop);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(404, "文章不存在");
        }
        article.setStatus(status);
        articleMapper.updateById(article);
        log.info("更新文章状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
