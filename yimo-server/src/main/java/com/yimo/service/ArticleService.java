package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.Article;

/**
 * 文章服务接口 - 提供文章的增删改查、分页、搜索、分类筛选及浏览统计功能
 *
 * @author yimo-team
 */
public interface ArticleService {

    /**
     * 根据ID查询文章详情
     *
     * @param id 文章ID
     * @return 文章实体
     */
    Result<Article> getById(Long id);

    /**
     * 分页查询文章列表
     *
     * @param request 分页请求参数（支持keyword搜索、categoryId分类筛选、status状态筛选）
     * @return 分页文章列表
     */
    Result<PageResult<Article>> list(PageRequest request);

    /**
     * 新增文章
     *
     * @param article 文章实体
     * @return 新增后的文章实体
     */
    Result<Article> create(Article article);

    /**
     * 更新文章
     *
     * @param article 文章实体（需包含ID）
     * @return 更新后的文章实体
     */
    Result<Article> update(Article article);

    /**
     * 删除文章（逻辑删除）
     *
     * @param id 文章ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 增加文章浏览次数
     *
     * @param id 文章ID
     * @return 操作结果
     */
    Result<Void> incrementViewCount(Long id);

    /**
     * 增加文章点赞次数
     *
     * @param id 文章ID
     * @return 操作结果
     */
    Result<Void> incrementLikeCount(Long id);

    /**
     * 更新文章置顶状态
     *
     * @param id   文章ID
     * @param isTop 是否置顶（0-否 1-是）
     * @return 操作结果
     */
    Result<Void> updateTopStatus(Long id, Integer isTop);

    /**
     * 更新文章发布状态
     *
     * @param id     文章ID
     * @param status 状态值（0-草稿 1-已发布 2-下架）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
