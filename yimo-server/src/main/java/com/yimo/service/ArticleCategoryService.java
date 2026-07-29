package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.ArticleCategory;

import java.util.List;

/**
 * 文章分类服务接口 - 提供文章分类的增删改查及启用分类列表功能
 *
 * @author yimo-team
 */
public interface ArticleCategoryService {

    /**
     * 根据ID查询文章分类
     *
     * @param id 分类ID
     * @return 文章分类实体
     */
    Result<ArticleCategory> getById(Long id);

    /**
     * 分页查询文章分类列表
     *
     * @param request 分页请求参数（支持keyword搜索、status筛选）
     * @return 分页文章分类列表
     */
    Result<PageResult<ArticleCategory>> list(PageRequest request);

    /**
     * 查询所有已启用的文章分类，按sortOrder正序排列
     *
     * @return 已启用的文章分类列表
     */
    Result<List<ArticleCategory>> listEnabled();

    /**
     * 新增文章分类
     *
     * @param category 文章分类实体
     * @return 新增后的文章分类实体
     */
    Result<ArticleCategory> create(ArticleCategory category);

    /**
     * 更新文章分类
     *
     * @param category 文章分类实体（需包含ID）
     * @return 更新后的文章分类实体
     */
    Result<ArticleCategory> update(ArticleCategory category);

    /**
     * 删除文章分类（逻辑删除）
     *
     * @param id 分类ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 更新文章分类状态
     *
     * @param id     分类ID
     * @param status 状态值（0-禁用 1-启用）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
