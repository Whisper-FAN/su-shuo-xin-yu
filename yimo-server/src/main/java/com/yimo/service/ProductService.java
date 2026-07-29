package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.Product;

import java.util.List;

/**
 * 产品服务接口 - 提供产品的增删改查、分页、多维筛选、热销推荐及浏览销售统计功能
 *
 * @author yimo-team
 */
public interface ProductService {

    /**
     * 根据ID查询产品详情
     *
     * @param id 产品ID
     * @return 产品实体
     */
    Result<Product> getById(Long id);

    /**
     * 分页查询产品列表，支持按分类、关键词、状态筛选
     *
     * @param request 分页请求参数（支持keyword搜索、categoryId分类筛选、status状态筛选）
     * @return 分页产品列表
     */
    Result<PageResult<Product>> list(PageRequest request);

    /**
     * 查询热门产品列表
     *
     * @param limit 返回数量限制
     * @return 热门产品列表（按isHot标识和销量排序）
     */
    Result<List<Product>> listHot(Integer limit);

    /**
     * 查询推荐产品列表
     *
     * @param limit 返回数量限制
     * @return 推荐产品列表（按isRecommend标识排序）
     */
    Result<List<Product>> listRecommended(Integer limit);

    /**
     * 根据生肖ID查询关联产品列表
     *
     * @param zodiacId 生肖ID
     * @param request  分页请求参数
     * @return 分页产品列表
     */
    Result<PageResult<Product>> listByZodiac(Long zodiacId, PageRequest request);

    /**
     * 新增产品
     *
     * @param product 产品实体
     * @return 新增后的产品实体
     */
    Result<Product> create(Product product);

    /**
     * 更新产品信息
     *
     * @param product 产品实体（需包含ID）
     * @return 更新后的产品实体
     */
    Result<Product> update(Product product);

    /**
     * 删除产品（逻辑删除）
     *
     * @param id 产品ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 增加产品浏览次数
     *
     * @param id 产品ID
     * @return 操作结果
     */
    Result<Void> incrementViewCount(Long id);

    /**
     * 增加产品销量
     *
     * @param id       产品ID
     * @param quantity 增加数量
     * @return 操作结果
     */
    Result<Void> incrementSales(Long id, Integer quantity);

    /**
     * 更新产品状态
     *
     * @param id     产品ID
     * @param status 状态值（0-下架 1-上架）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
