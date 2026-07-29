package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.OperationLog;

import java.util.Map;

/**
 * 管理后台服务接口 - 提供管理端仪表盘概览及各模块管理数据聚合查询功能
 *
 * @author yimo-team
 */
public interface AdminService {

    /**
     * 获取管理端仪表盘总览数据
     *
     * @return 包含用户总数、文章总数、产品总数、测试总数、今日PV/UV、
     *         今日新增用户、今日订单数、近7日趋势数据等概览信息的Map
     */
    Result<Map<String, Object>> getDashboardOverview();

    /**
     * 获取指定模块的管理数据
     *
     * @param module 模块名称（user/article/product/zodiac/test/feedback/gallery/statistics）
     * @return 该模块的管理数据（含总数、今日新增、各状态统计等）
     */
    Result<Map<String, Object>> getModuleStats(String module);

    /**
     * 分页查询操作日志
     *
     * @param request 分页请求参数（支持keyword搜索、时间范围筛选）
     * @return 分页操作日志列表
     */
    Result<PageResult<OperationLog>> listOperationLogs(PageRequest request);

    /**
     * 清空指定模块的缓存
     *
     * @param module 模块名称
     * @return 操作结果
     */
    Result<Void> clearCache(String module);
}
