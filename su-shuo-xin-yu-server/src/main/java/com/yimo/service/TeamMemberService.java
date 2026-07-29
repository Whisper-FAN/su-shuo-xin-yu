package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.TeamMember;

import java.util.List;

/**
 * 团队成员服务接口 - 提供团队成员的增删改查及列表功能
 *
 * @author yimo-team
 */
public interface TeamMemberService {

    /**
     * 根据ID查询团队成员
     *
     * @param id 成员ID
     * @return 团队成员实体
     */
    Result<TeamMember> getById(Long id);

    /**
     * 分页查询团队成员列表
     *
     * @param request 分页请求参数（支持keyword搜索、status筛选）
     * @return 分页团队成员列表
     */
    Result<PageResult<TeamMember>> list(PageRequest request);

    /**
     * 查询所有团队成员，按sortOrder正序排列
     *
     * @return 所有团队成员列表
     */
    Result<List<TeamMember>> listAll();

    /**
     * 查询核心团队成员列表
     *
     * @return 核心团队成员列表（isCore=1）
     */
    Result<List<TeamMember>> listCore();

    /**
     * 新增团队成员
     *
     * @param member 团队成员实体
     * @return 新增后的团队成员实体
     */
    Result<TeamMember> create(TeamMember member);

    /**
     * 更新团队成员信息
     *
     * @param member 团队成员实体（需包含ID）
     * @return 更新后的团队成员实体
     */
    Result<TeamMember> update(TeamMember member);

    /**
     * 删除团队成员（逻辑删除）
     *
     * @param id 成员ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 更新团队成员状态
     *
     * @param id     成员ID
     * @param status 状态值（0-禁用 1-启用）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
