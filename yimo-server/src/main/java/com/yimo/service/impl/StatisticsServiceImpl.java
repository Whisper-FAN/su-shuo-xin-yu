package com.yimo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.*;
import com.yimo.mapper.*;
import com.yimo.service.StatisticsService;
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
 * 统计服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsDailyMapper statisticsDailyMapper;
    private final UserBehaviorMapper userBehaviorMapper;
    private final TestRecordMapper testRecordMapper;
    private final ProductMapper productMapper;
    private final ZodiacMapper zodiacMapper;
    private final UserMapper userMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Result<Map<String, Object>> getDashboardStats() {
        Map<String, Object> result = new LinkedHashMap<>();

        // Total PV and UV across all daily records
        List<StatisticsDaily> allStats = statisticsDailyMapper.selectList(null);
        Long totalPv = allStats.stream().mapToLong(s -> s.getPv() != null ? s.getPv() : 0).sum();
        Long totalUv = allStats.stream().mapToLong(s -> s.getUv() != null ? s.getUv() : 0).sum();
        Long totalTestCount = allStats.stream().mapToLong(s -> s.getTestCount() != null ? s.getTestCount() : 0).sum();
        Integer totalProductClick = allStats.stream().mapToInt(s -> s.getProductClick() != null ? s.getProductClick() : 0).sum();

        result.put("totalPv", totalPv);
        result.put("totalUv", totalUv);
        result.put("totalTestCount", totalTestCount);
        result.put("totalProductClick", totalProductClick);
        result.put("totalUsers", userMapper.selectCount(null));
        result.put("totalProducts", productMapper.selectCount(null));

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
        result.put("todayProductClick", todayStats != null && todayStats.getProductClick() != null ? todayStats.getProductClick() : 0);

        // Most popular zodiac
        if (todayStats != null && todayStats.getMostPopularZodiac() != null) {
            Zodiac zodiac = zodiacMapper.selectById(todayStats.getMostPopularZodiac());
            if (zodiac != null) {
                Map<String, Object> popularZodiac = new LinkedHashMap<>();
                popularZodiac.put("id", zodiac.getId());
                popularZodiac.put("name", zodiac.getName());
                popularZodiac.put("imageUrl", zodiac.getImageUrl());
                result.put("mostPopularZodiac", popularZodiac);
            }
        }

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
            dailyStats.add(dayMap);
        }
        result.put("dailyStats", dailyStats);

        return Result.success(result);
    }

    @Override
    public Result<Void> recordUserBehavior(UserBehavior behavior) {
        if (behavior.getCreateTime() == null) {
            behavior.setCreateTime(LocalDateTime.now());
        }
        userBehaviorMapper.insert(behavior);

        // Update daily statistics
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.plusDays(1).atStartOfDay();

        StatisticsDaily daily = statisticsDailyMapper.selectOne(
                new LambdaQueryWrapper<StatisticsDaily>()
                        .ge(StatisticsDaily::getStatDate, todayStart)
                        .lt(StatisticsDaily::getStatDate, todayEnd));

        if (daily == null) {
            daily = new StatisticsDaily();
            daily.setStatDate(todayStart);
            daily.setPv(0L);
            daily.setUv(0L);
            daily.setTestCount(0);
            daily.setProductClick(0);
            daily.setShareCount(0);
            daily.setNewUserCount(0);
            statisticsDailyMapper.insert(daily);
        }

        // Increment counters based on behavior type
        daily.setPv((daily.getPv() != null ? daily.getPv() : 0) + 1);
        if ("test_finish".equals(behavior.getBehaviorType())) {
            daily.setTestCount((daily.getTestCount() != null ? daily.getTestCount() : 0) + 1);
        }
        if ("product_click".equals(behavior.getBehaviorType())) {
            daily.setProductClick((daily.getProductClick() != null ? daily.getProductClick() : 0) + 1);
        }
        if ("share_click".equals(behavior.getBehaviorType())) {
            daily.setShareCount((daily.getShareCount() != null ? daily.getShareCount() : 0) + 1);
        }
        statisticsDailyMapper.updateById(daily);

        return Result.success();
    }

    @Override
    public Result<List<StatisticsDaily>> getDailyReport(String startDate, String endDate) {
        LocalDateTime start = LocalDate.parse(startDate, DATE_FORMATTER).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate, DATE_FORMATTER).plusDays(1).atStartOfDay();

        List<StatisticsDaily> list = statisticsDailyMapper.selectList(
                new LambdaQueryWrapper<StatisticsDaily>()
                        .ge(StatisticsDaily::getStatDate, start)
                        .lt(StatisticsDaily::getStatDate, end)
                        .orderByAsc(StatisticsDaily::getStatDate));
        return Result.success(list);
    }

    @Override
    public Result<PageResult<StatisticsDaily>> getDailyReport(PageRequest request) {
        Page<StatisticsDaily> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<StatisticsDaily> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(StatisticsDaily::getStatDate);

        Page<StatisticsDaily> result = statisticsDailyMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<List<Map<String, Object>>> getVisitorTrend(Integer days) {
        if (days == null || days <= 0) {
            days = 7;
        }
        LocalDate today = LocalDate.now();

        List<Map<String, Object>> trend = new ArrayList<>();
        for (int i = days - 1; i >= 0; i--) {
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
            trend.add(dayMap);
        }
        return Result.success(trend);
    }

    @Override
    public Result<List<Map<String, Object>>> getMostPopularZodiac(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 12;
        }

        // Count test records grouped by zodiacId
        List<TestRecord> allRecords = testRecordMapper.selectList(null);
        Map<Long, Long> zodiacCount = new LinkedHashMap<>();
        for (TestRecord record : allRecords) {
            if (record.getZodiacId() != null) {
                zodiacCount.merge(record.getZodiacId(), 1L, Long::sum);
            }
        }

        // Sort by count descending and limit
        List<Map<String, Object>> result = zodiacCount.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> {
                    Zodiac zodiac = zodiacMapper.selectById(entry.getKey());
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("zodiacId", entry.getKey());
                    map.put("name", zodiac != null ? zodiac.getName() : "");
                    map.put("imageUrl", zodiac != null ? zodiac.getImageUrl() : "");
                    map.put("count", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());

        return Result.success(result);
    }
}
