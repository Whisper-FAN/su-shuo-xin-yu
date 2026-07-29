package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.Gallery;

/**
 * 图库服务接口 - 提供图库的增删改查、分页及分类筛选功能
 *
 * @author yimo-team
 */
public interface GalleryService {

    /**
     * 根据ID查询图库图片
     *
     * @param id 图片ID
     * @return 图库实体
     */
    Result<Gallery> getById(Long id);

    /**
     * 分页查询图库列表，支持按分类筛选
     *
     * @param request 分页请求参数（支持keyword搜索、categoryId用于分类筛选、status筛选）
     * @return 分页图库列表
     */
    Result<PageResult<Gallery>> list(PageRequest request);

    /**
     * 根据分类查询图库列表
     *
     * @param category 分类名称
     * @param request  分页请求参数
     * @return 分页图库列表
     */
    Result<PageResult<Gallery>> listByCategory(String category, PageRequest request);

    /**
     * 新增图库图片
     *
     * @param gallery 图库实体
     * @return 新增后的图库实体
     */
    Result<Gallery> create(Gallery gallery);

    /**
     * 更新图库图片信息
     *
     * @param gallery 图库实体（需包含ID）
     * @return 更新后的图库实体
     */
    Result<Gallery> update(Gallery gallery);

    /**
     * 删除图库图片（逻辑删除）
     *
     * @param id 图片ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 更新图库图片状态
     *
     * @param id     图片ID
     * @param status 状态值（0-禁用 1-启用）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
