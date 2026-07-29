package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.User;

import java.util.List;

/**
 * 用户服务接口 - 提供用户的增删改查及分页列表功能
 *
 * @author yimo-team
 */
public interface UserService {

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户实体
     */
    Result<User> getById(Long id);

    /**
     * 分页查询用户列表
     *
     * @param request 分页请求参数（支持keyword搜索、status筛选）
     * @return 分页用户列表
     */
    Result<PageResult<User>> list(PageRequest request);

    /**
     * 新增用户
     *
     * @param user 用户实体
     * @return 新增后的用户实体
     */
    Result<User> create(User user);

    /**
     * 更新用户信息
     *
     * @param user 用户实体（需包含ID）
     * @return 更新后的用户实体
     */
    Result<User> update(User user);

    /**
     * 删除用户（逻辑删除）
     *
     * @param id 用户ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 用户ID列表
     * @return 操作结果
     */
    Result<Void> deleteBatch(List<Long> ids);

    /**
     * 更新用户状态
     *
     * @param id     用户ID
     * @param status 状态值（0-禁用 1-启用）
     * @return 操作结果
     */
    Result<Void> updateStatus(Long id, Integer status);
}
