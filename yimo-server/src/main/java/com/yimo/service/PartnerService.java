package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.Partner;

import java.util.List;

/**
 * 合作伙伴服务接口 - 提供合作伙伴的增删改查及启用列表功能
 *
 * @author yimo-team
 */
public interface PartnerService {

    /**
     * 根据ID查询合作伙伴
     *
     * @param id 合作伙伴ID
     * @return 合作伙伴实体
     */
    Result<Partner> getById(Long id);

    /**
     * 分页查询合作伙伴列表
     *
     * @param request 分页请求参数（支持keyword搜索、status筛选）
     * @return 分页合作伙伴列表
     */
    Result<PageResult<Partner>> list(PageRequest request);

    /**
     * 查询所有已启用的合作伙伴，按sortOrder正序排列
     *
     * @return 已启用的合作伙伴列表
     */
    Result<List<Partner>> listEnabled();

    /**
     * 新增合作伙伴
     *
     * @param partner 合作伙伴实体
     * @return 新增后的合作伙伴实体
     */
    Result<Partner> create(Partner partner);

    /**
     * 更新合作伙伴信息
     *
     * @param partner 合作伙伴实体（需包含ID）
     * @return 更新后的合作伙伴实体
     */
    Result<Partner> update(Partner partner);

    /**
     * 删除合作伙伴（逻辑删除）
     *
     * @param id 合作伙伴ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 更新合作伙伴状态
     *
     * @param id     合作伙伴ID
     * @param status 状态值（0-禁用 1-启用）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
