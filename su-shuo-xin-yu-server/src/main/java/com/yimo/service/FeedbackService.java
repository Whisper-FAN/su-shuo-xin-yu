package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.Feedback;

import java.util.List;

/**
 * 反馈服务接口 - 提供用户反馈创建、分页查询及处理标记功能
 *
 * @author yimo-team
 */
public interface FeedbackService {

    /**
     * 根据ID查询反馈详情
     *
     * @param id 反馈ID
     * @return 反馈实体
     */
    Result<Feedback> getById(Long id);

    /**
     * 分页查询反馈列表（管理员使用）
     *
     * @param request 分页请求参数（支持keyword搜索、status用于isHandled筛选）
     * @return 分页反馈列表
     */
    Result<PageResult<Feedback>> list(PageRequest request);

    /**
     * 创建用户反馈
     *
     * @param feedback 反馈实体
     * @return 新增后的反馈实体
     */
    Result<Feedback> create(Feedback feedback);

    /**
     * 标记反馈为已处理
     *
     * @param id         反馈ID
     * @param handleNote 处理备注
     * @return 操作结果
     */
    Result<Void> markAsHandled(Long id, String handleNote);

    /**
     * 批量标记反馈为已处理
     *
     * @param ids        反馈ID列表
     * @param handleNote 处理备注
     * @return 操作结果
     */
    Result<Void> markAsHandledBatch(List<Long> ids, String handleNote);

    /**
     * 删除反馈（逻辑删除）
     *
     * @param id 反馈ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);
}
