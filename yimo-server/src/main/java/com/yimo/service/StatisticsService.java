package com.yimo.service;

import com.yimo.common.PageResult;
import com.yimo.common.PageRequest;
import com.yimo.common.Result;
import com.yimo.entity.StatisticsDaily;
import com.yimo.entity.UserBehavior;

import java.util.List;
import java.util.Map;

/**
 * 统计服务接口 - 提供仪表盘统计数据、用户行为记录及每日统计报表功能
 *
 * @author yimo-team
 */
public interface StatisticsService {

    /**
     * 获取仪表盘核心统计数据
     *
     * @return 包含PV、UV、测试次数、产品点击量、分享次数、新增用户、最受欢迎生肖、
     *         总收入等统计指标的Map
     */
    Result<Map<String, Object>> getDashboardStats();

    /**
     * 记录用户行为
     *
     * @param behavior 用户行为实体
     * @return 操作结果
     */
    Result<Void> recordUserBehavior(UserBehavior behavior);

    /**
     * 查询每日统计报表
     *
     * @param startDate 开始日期（格式：yyyy-MM-dd）
     * @param endDate   结束日期（格式：yyyy-MM-dd）
     * @return 每日统计数据列表
     */
    Result<List<StatisticsDaily>> getDailyReport(String startDate, String endDate);

    /**
     * 分页查询每日统计报表
     *
     * @param request 分页请求参数
     * @return 分页每日统计数据列表
     */
    Result<PageResult<StatisticsDaily>> getDailyReport(PageRequest request);

    /**
     * 查询每日访客趋势数据
     *
     * @param days 查询最近天数
     * @return 每日访客数量列表
     */
    Result<List<Map<String, Object>>> getVisitorTrend(Integer days);

    /**
     * 查询最受欢迎的生肖排名
     *
     * @param limit 返回数量限制
     * @return 按受欢迎程度排序的生肖列表
     */
    Result<List<Map<String, Object>>> getMostPopularZodiac(Integer limit);
}
