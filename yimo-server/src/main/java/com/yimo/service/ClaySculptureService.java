package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.ClaySculpture;

/**
 * 泥塑作品服务接口 - 提供泥塑作品的增删改查、分页及多维度筛选功能
 *
 * @author yimo-team
 */
public interface ClaySculptureService {

    /**
     * 根据ID查询泥塑作品详情
     *
     * @param id 作品ID
     * @return 泥塑作品实体
     */
    Result<ClaySculpture> getById(Long id);

    /**
     * 分页查询泥塑作品列表，支持按生肖ID、工艺类型筛选
     *
     * @param request 分页请求参数（支持keyword搜索、categoryId用于生肖筛选、status筛选）
     *                扩展参数：zodiacId（生肖ID）、craftType（工艺类型）
     * @return 分页泥塑作品列表
     */
    Result<PageResult<ClaySculpture>> list(PageRequest request);

    /**
     * 根据生肖ID查询泥塑作品列表
     *
     * @param zodiacId 生肖ID
     * @return 泥塑作品列表
     */
    Result<PageResult<ClaySculpture>> listByZodiac(Long zodiacId, PageRequest request);

    /**
     * 根据工艺类型查询泥塑作品列表
     *
     * @param craftType 工艺类型
     * @param request   分页请求参数
     * @return 分页泥塑作品列表
     */
    Result<PageResult<ClaySculpture>> listByCraftType(String craftType, PageRequest request);

    /**
     * 新增泥塑作品
     *
     * @param sculpture 泥塑作品实体
     * @return 新增后的泥塑作品实体
     */
    Result<ClaySculpture> create(ClaySculpture sculpture);

    /**
     * 更新泥塑作品
     *
     * @param sculpture 泥塑作品实体（需包含ID）
     * @return 更新后的泥塑作品实体
     */
    Result<ClaySculpture> update(ClaySculpture sculpture);

    /**
     * 删除泥塑作品（逻辑删除）
     *
     * @param id 作品ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 更新泥塑作品状态
     *
     * @param id     作品ID
     * @param status 状态值（0-禁用 1-启用）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
