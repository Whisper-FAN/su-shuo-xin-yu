package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.*;
import com.yimo.mapper.*;
import com.yimo.service.AdminService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 管理后台服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;
    private final ProductMapper productMapper;
    private final TestRecordMapper testRecordMapper;
    private final StatisticsDailyMapper statisticsDailyMapper;
    private final FeedbackMapper feedbackMapper;
    private final OperationLogMapper operationLogMapper;
    private final ZodiacMapper zodiacMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Result<Map<String, Object>> getDashboardOverview() {
        Map<String, Object> result = new LinkedHashMap<>();

        // Entity counts
        result.put("userCount", userMapper.selectCount(null));
        result.put("articleCount", articleMapper.selectCount(null));
        result.put("productCount", productMapper.selectCount(null));
        result.put("testCount", testRecordMapper.selectCount(null));
        result.put("pendingFeedbackCount", feedbackMapper.selectCount(
                new LambdaQueryWrapper<Feedback>().eq(Feedback::getIsHandled, 0)));

        // Today's stats
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.plusDays(1).atStartOfDay();

        StatisticsDaily todayStats = statisticsDailyMapper.selectOne(
                new LambdaQueryWrapper<StatisticsDaily>()
                        .ge(StatisticsDaily::getStatDate, todayStart)
                        .lt(StatisticsDaily::getStatDate, todayEnd));

        result.put("todayPv", todayStats != null && todayStats.getPv() != null ? todayStats.getPv() : 0L);
        result.put("todayUv", todayStats != null && todayStats.getUv() != null ? todayStats.getUv() : 0L);
        result.put("todayTestCount", todayStats != null && todayStats.getTestCount() != null ? todayStats.getTestCount() : 0);

        Long todayNewUsers = userMapper.selectCount(
                new LambdaQueryWrapper<User>()
                        .ge(User::getCreateTime, todayStart)
                        .lt(User::getCreateTime, todayEnd));
        result.put("todayNewUsers", todayNewUsers);

        Long todayTests = testRecordMapper.selectCount(
                new LambdaQueryWrapper<TestRecord>()
                        .ge(TestRecord::getCreateTime, todayStart)
                        .lt(TestRecord::getCreateTime, todayEnd));
        result.put("todayTests", todayTests);

        // Recent test records (limit 10)
        List<TestRecord> recentTests = testRecordMapper.selectList(
                new LambdaQueryWrapper<TestRecord>()
                        .orderByDesc(TestRecord::getCreateTime)
                        .last("LIMIT 10"));

        List<Map<String, Object>> recentTestList = recentTests.stream().map(record -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", record.getId());
            map.put("userId", record.getUserId());
            Zodiac zodiac = record.getZodiacId() != null ? zodiacMapper.selectById(record.getZodiacId()) : null;
            map.put("zodiacName", zodiac != null ? zodiac.getName() : "");
            map.put("zodiacImage", zodiac != null ? zodiac.getImageUrl() : "");
            map.put("testDuration", record.getTestDuration());
            map.put("createTime", record.getCreateTime());

            if (record.getUserId() != null) {
                User user = userMapper.selectById(record.getUserId());
                map.put("userNickname", user != null ? user.getNickname() : "匿名用户");
            } else {
                map.put("userNickname", "匿名用户");
            }
            return map;
        }).collect(Collectors.toList());
        result.put("recentTestRecords", recentTestList);

        // Popular products (top 5 by sales)
        List<Product> popularProducts = productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getStatus, 1)
                        .orderByDesc(Product::getSales)
                        .last("LIMIT 5"));

        List<Map<String, Object>> popularProductList = popularProducts.stream().map(p -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", p.getId());
            map.put("name", p.getName());
            map.put("imageUrl", p.getImageUrl());
            map.put("price", p.getPrice());
            map.put("sales", p.getSales());
            return map;
        }).collect(Collectors.toList());
        result.put("popularProducts", popularProductList);

        // Last 7 days daily stats
        List<Map<String, Object>> dailyStats = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            LocalDateTime dayStart = date.atStartOfDay();
            LocalDateTime dayEnd = date.plusDays(1).atStartOfDay();

            StatisticsDaily daily = statisticsDailyMapper.selectOne(
                    new LambdaQueryWrapper<StatisticsDaily>()
                            .ge(StatisticsDaily::getStatDate, dayStart)
                            .lt(StatisticsDaily::getStatDate, dayEnd));

            Map<String, Object> dayMap = new LinkedHashMap<>();
            dayMap.put("date", date.format(DATE_FORMATTER));
            dayMap.put("pv", daily != null && daily.getPv() != null ? daily.getPv() : 0L);
            dayMap.put("uv", daily != null && daily.getUv() != null ? daily.getUv() : 0L);
            dayMap.put("testCount", daily != null && daily.getTestCount() != null ? daily.getTestCount() : 0);
            dayMap.put("productClick", daily != null && daily.getProductClick() != null ? daily.getProductClick() : 0);
            dayMap.put("newUserCount", daily != null && daily.getNewUserCount() != null ? daily.getNewUserCount() : 0);
            dailyStats.add(dayMap);
        }
        result.put("dailyStats", dailyStats);

        return Result.success(result);
    }

    @Override
    public Result<Map<String, Object>> getModuleStats(String module) {
        Map<String, Object> result = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.plusDays(1).atStartOfDay();

        switch (module.toLowerCase()) {
            case "user":
                result.put("total", userMapper.selectCount(null));
                result.put("todayNew", userMapper.selectCount(
                        new LambdaQueryWrapper<User>()
                                .ge(User::getCreateTime, todayStart)
                                .lt(User::getCreateTime, todayEnd)));
                break;
            case "article":
                result.put("total", articleMapper.selectCount(null));
                result.put("published", articleMapper.selectCount(
                        new LambdaQueryWrapper<Article>().eq(Article::getStatus, 1)));
                result.put("draft", articleMapper.selectCount(
                        new LambdaQueryWrapper<Article>().eq(Article::getStatus, 0)));
                break;
            case "product":
                result.put("total", productMapper.selectCount(null));
                result.put("onSale", productMapper.selectCount(
                        new LambdaQueryWrapper<Product>().eq(Product::getStatus, 1)));
                Long totalSales = productMapper.selectList(null).stream()
                        .mapToLong(p -> p.getSales() != null ? p.getSales().longValue() : 0).sum();
                result.put("totalSales", totalSales);
                break;
            case "zodiac":
                result.put("total", zodiacMapper.selectCount(null));
                result.put("enabled", zodiacMapper.selectCount(
                        new LambdaQueryWrapper<Zodiac>().eq(Zodiac::getStatus, 1)));
                break;
            case "test":
                result.put("total", testRecordMapper.selectCount(null));
                result.put("today", testRecordMapper.selectCount(
                        new LambdaQueryWrapper<TestRecord>()
                                .ge(TestRecord::getCreateTime, todayStart)
                                .lt(TestRecord::getCreateTime, todayEnd)));
                break;
            case "feedback":
                result.put("total", feedbackMapper.selectCount(null));
                result.put("pending", feedbackMapper.selectCount(
                        new LambdaQueryWrapper<Feedback>().eq(Feedback::getIsHandled, 0)));
                result.put("handled", feedbackMapper.selectCount(
                        new LambdaQueryWrapper<Feedback>().eq(Feedback::getIsHandled, 1)));
                break;
            case "statistics":
                StatisticsDaily todayStats = statisticsDailyMapper.selectOne(
                        new LambdaQueryWrapper<StatisticsDaily>()
                                .ge(StatisticsDaily::getStatDate, todayStart)
                                .lt(StatisticsDaily::getStatDate, todayEnd));
                result.put("todayPv", todayStats != null && todayStats.getPv() != null ? todayStats.getPv() : 0L);
                result.put("todayUv", todayStats != null && todayStats.getUv() != null ? todayStats.getUv() : 0L);
                break;
            default:
                result.put("message", "未知模块: " + module);
                break;
        }

        return Result.success(result);
    }

    @Override
    public Result<PageResult<OperationLog>> listOperationLogs(PageRequest request) {
        Page<OperationLog> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.and(w -> w.like(OperationLog::getModule, request.getKeyword())
                    .or()
                    .like(OperationLog::getAction, request.getKeyword())
                    .or()
                    .like(OperationLog::getDescription, request.getKeyword()));
        }

        wrapper.orderByDesc(OperationLog::getCreateTime);

        Page<OperationLog> result = operationLogMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<Void> clearCache(String module) {
        log.info("清空模块缓存: module={}", module);
        // Cache clearing logic placeholder - integrate with Redis when configured
        return Result.success("缓存已清空", null);
    }
}
