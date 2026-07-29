package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.ProductCategory;

import java.util.List;

/**
 * 产品分类服务接口 - 提供产品分类的增删改查及启用分类列表功能
 *
 * @author yimo-team
 */
public interface ProductCategoryService {

    /**
     * 根据ID查询产品分类
     *
     * @param id 分类ID
     * @return 产品分类实体
     */
    Result<ProductCategory> getById(Long id);

    /**
     * 分页查询产品分类列表
     *
     * @param request 分页请求参数（支持keyword搜索、status筛选）
     * @return 分页产品分类列表
     */
    Result<PageResult<ProductCategory>> list(PageRequest request);

    /**
     * 查询所有已启用的产品分类，按sortOrder正序排列
     *
     * @return 已启用的产品分类列表
     */
    Result<List<ProductCategory>> listEnabled();

    /**
     * 新增产品分类
     *
     * @param category 产品分类实体
     * @return 新增后的产品分类实体
     */
    Result<ProductCategory> create(ProductCategory category);

    /**
     * 更新产品分类
     *
     * @param category 产品分类实体（需包含ID）
     * @return 更新后的产品分类实体
     */
    Result<ProductCategory> update(ProductCategory category);

    /**
     * 删除产品分类（逻辑删除）
     *
     * @param id 分类ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 更新产品分类状态
     *
     * @param id     分类ID
     * @param status 状态值（0-禁用 1-启用）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
