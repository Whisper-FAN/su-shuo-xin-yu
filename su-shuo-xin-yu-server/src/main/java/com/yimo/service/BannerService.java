package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.Banner;

import java.util.List;

/**
 * 轮播图服务接口 - 提供轮播图的增删改查及启用列表功能
 *
 * @author yimo-team
 */
public interface BannerService {

    /**
     * 根据ID查询轮播图
     *
     * @param id 轮播图ID
     * @return 轮播图实体
     */
    Result<Banner> getById(Long id);

    /**
     * 分页查询轮播图列表
     *
     * @param request 分页请求参数（支持keyword搜索、status筛选）
     * @return 分页轮播图列表
     */
    Result<PageResult<Banner>> list(PageRequest request);

    /**
     * 查询所有已启用的轮播图，按sortOrder正序排列
     *
     * @return 已启用的轮播图列表
     */
    Result<List<Banner>> listEnabled();

    /**
     * 新增轮播图
     *
     * @param banner 轮播图实体
     * @return 新增后的轮播图实体
     */
    Result<Banner> create(Banner banner);

    /**
     * 更新轮播图
     *
     * @param banner 轮播图实体（需包含ID）
     * @return 更新后的轮播图实体
     */
    Result<Banner> update(Banner banner);

    /**
     * 删除轮播图（逻辑删除）
     *
     * @param id 轮播图ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 更新轮播图状态
     *
     * @param id     轮播图ID
     * @param status 状态值（0-禁用 1-启用）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
